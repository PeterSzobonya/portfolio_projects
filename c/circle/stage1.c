#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

typedef enum {false, true} bool;


//vector length = sqrt(x^2+y^2)

float randomFloat()
{
    double r = ((double)rand() / RAND_MAX) * 2;
    return r > 1 ? r/2.0*-1: r/2.0;
}

void generatePoints(int numOfPoints, bool* points){
    
    for(int i=0; i< numOfPoints; i++){
        double x = randomFloat();
        double y = randomFloat();
        points[i] = (sqrt((pow(x,2)+pow(y,2)))<=1);
    }
};

int main(int argc, char const *argv[])
{
    if(argc != 2){
        perror("number of arguments must be 1\n");
        return EXIT_FAILURE;
    } else if(atoi(argv[1]) <= 0){
        perror("first argument must be bigger than 0\n");
        return EXIT_FAILURE;
    }

    srand((unsigned int)time(NULL));
    int numOfPoints = atoi(argv[1]);
    bool* points = malloc(sizeof(bool) * numOfPoints);

    generatePoints(numOfPoints,points);

    int trues=0;
    int falses=0;

    for(int i=0; i<numOfPoints; i++){
        if(points[i]){
            trues +=1;
        } else {
            falses +=1;
        }
    }

    printf("area: %f\n",trues/(numOfPoints*1.0)*4);

    return EXIT_SUCCESS;
}
