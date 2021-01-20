#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <math.h>
#include <stdbool.h>

//defining workspace

typedef struct 
{
   int tid;  // thread id number

   int numOfPoints;	//number of points generated
   int trueCount;	//counting if point is within the circle
   int falseCount;	//counting if point is not within the circle
}Workspace;


//method for random point generation

double randomPoint(unsigned int *seed)
{
    double rp = ((double)rand_r(seed) / RAND_MAX);
    return rp;
}

//generating all random points

void generatePoints(int numOfPoints, bool* points){
   unsigned int seed = time(NULL);

   for(int i=0; i< numOfPoints; i++){
      double x = randomPoint(&seed);
      double y = randomPoint(&seed);
      points[i] = (sqrt((pow(x,2)+pow(y,2)))<=1);		//if point is x^2 + y^2 <= 1 then it is within the circle
   }
};

//creating worker

void* worker(void *ws)
{
   Workspace *workspace = (Workspace*)ws;


   bool* points = malloc(sizeof(bool) * workspace->numOfPoints);	//dynamic memory allocation
   generatePoints(workspace->numOfPoints,points);

   workspace->trueCount=0;					//initalising counts to 0
   workspace->falseCount=0;

   for(int i=0; i<workspace->numOfPoints; i++){			
      if(points[i]){						//counting if points are within the circle
         workspace->trueCount +=1;
      } else {							//counting if points are not within the circle
         workspace->falseCount +=1;
      }
   }
   free(points);						//freeing memory


   return NULL;
}

//main method

int main(int argc, char *argv[])
{
   struct timespec start, finish;			//starting time counter
   double elapsed;
   clock_gettime(CLOCK_MONOTONIC, &start);

   srand((unsigned int)time(NULL));			
   int numOfPoints = atoi(argv[1]);			//passing argument value (how many points to generate) to numOfPoints
   int threads = atoi(argv[2]);				//passing argument value (how many threads to create) to threads

   int trueCount=0;					//initalising counts to 0
   int falseCount=0;

   Workspace workspace[threads];			//creating workspace
   pthread_t thread[threads];				

   for (int i = 0; i < threads; i++)
   {
      workspace[i].tid = i;
      workspace[i].numOfPoints = numOfPoints/threads;

      int t = pthread_create(&(thread[i]), NULL, worker, &(workspace[i]));

      if (t != 0)					//error handling
      {
         perror("pthread_create error");

         return EXIT_FAILURE;
      }
   }

   for (int i = 0; i < threads; i++)
   {
      if (pthread_join(thread[i], NULL) != 0)
      {
         perror("pthread_join error");			//error handling

         return EXIT_FAILURE;
      }

      //printf("worker %d has joined with %d points in the circle\n", i, workspace[i].trueCount);
     
     
      trueCount += workspace[i].trueCount;

   }

   clock_gettime(CLOCK_MONOTONIC, &finish);
   elapsed = (finish.tv_sec - start.tv_sec);
   elapsed += (finish.tv_nsec - start.tv_nsec) / 1000000000.0;

   printf("area: %f\ntime used: %f seconds\n",trueCount/(numOfPoints*1.0)*4,elapsed);		//printing time elapsed and estimated area

   return EXIT_SUCCESS;
}
