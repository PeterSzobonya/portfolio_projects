#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <math.h>

#define TIMESCALE (1000000)  // timescale on which processes sleep
#define MAX_SRR   (16)       // maximun number of serially reusable resources

long sharedTrues = 0;


typedef struct 
{
   int wid;    // worker I.D. number

   int nsrr;   // number of serially reusable resources
   long numberOfPoints;
   long trueCounter;   //counts the points inside the circle in the workspace
}
Workspace;



typedef enum {false, true} bool;

int resourcesCounter[MAX_SRR]={
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0
};

pthread_mutex_t mutex[MAX_SRR] =
{
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER,
   PTHREAD_MUTEX_INITIALIZER
};

float randomFloat( unsigned int *seed)
{
    double r = ((double)rand_r(seed) / RAND_MAX);
    return r;
}

void generatePoints(int numOfPoints, int* points){
   unsigned int seed = time(NULL);

   for(int i=0; i< numOfPoints; i++){
      double x = randomFloat(&seed);
      double y = randomFloat(&seed);
      *points = ((pow(x,2)+pow(y,2))<=1) ? *points+1 : *points;
   }
};

void* worker(void *workspace){
    Workspace *ws = (Workspace*) workspace;

    //worker started

    int points = 0;
    generatePoints(ws->numberOfPoints,&points);

    int srr[ws->nsrr];

    for (int i = 0; i < ws->nsrr; i++)
    {
        srr[i] = 0;
    }

    /*
    for (int i = ws->nsrr - 1; i > 0; i--)
    {
        int j = rand()%i;

        int tmp = srr[i];
        srr[i]  = srr[j];
        srr[j]  = tmp;
    }
    */

    for (int i = 0; i < ws->nsrr; i++)
    {
        //usleep(rand()%TIMESCALE);

        if(pthread_mutex_trylock(&(mutex[srr[i]])) == 0){
            resourcesCounter[i] += points;
            pthread_mutex_unlock(&(mutex[i]));
            pthread_exit(NULL);
        }
    }

    //printf("added points: %d", points);

   return NULL;
}

int main(int argc, char *argv[])
{
   srand(time(NULL));

   // parse command line args using getopt

   int threads   = 2;
   int resources = 1;
   long numberOfPoints = 0;
   int opt;
   
   char *usage = "Usage: %s [-h] [-t threads] [-r resources] [-p points]\n";

   while ((opt = getopt(argc, argv, "ht:r:p:")) != -1)
   {
      switch (opt)
      {
         case 'h':
         {
            fprintf(stderr, usage, argv[0]);

            return EXIT_SUCCESS;
         }
         case 't':
         {
            // see strtol for error handling?

            threads = atoi(optarg);

            break;
         }
         case 'r':
         {
            resources = atoi(optarg);

            break;
         }
         case 'p':
         {
             numberOfPoints = atoi(optarg);

             break;
         }
         default:
         {
            fprintf(stderr, usage, argv[0]);

            return EXIT_FAILURE;
         }
      }
   }

   // perform simulation

   Workspace workspace[threads];
   pthread_t thread[threads];


   for (int i = 0; i < threads; i++)
   {
      workspace[i].wid  = i;
      workspace[i].nsrr = resources;
      workspace[i].numberOfPoints = numberOfPoints/threads;

      int t = pthread_create(&(thread[i]), NULL, worker, &(workspace[i]));

      if (t != 0)
      {
         perror("pthread_create error");

         return EXIT_FAILURE;
      }
   }

   for (int i = 0; i < threads; i++)
   {
      if (pthread_join(thread[i], NULL) != 0)
      {
         perror("pthread_join error");

         return EXIT_FAILURE;
      }
      //printf("worker %d has joined\n", i);
   }

   for(int i=0; i<resources; i++){
       sharedTrues+=resourcesCounter[i];
   }

    printf("area: %f\n", sharedTrues/(numberOfPoints*1.0)*4);

   return EXIT_SUCCESS;
}