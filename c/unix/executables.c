#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <dirent.h>

#define BUFFER_SIZE (4096)
#define DELIM ":"

//method for appending two strings

char* append(const char* str1, char* str2){
  char * new_str ;
  if((new_str = malloc(strlen(str1)+strlen(str2)+1)) != NULL){
    new_str[0] = '\0';                // ensures the memory is an empty string
    strcat(new_str,str1);
    strcat(new_str,str2);
  }
  return new_str;
}


int main(int argc, char const *argv[]) {

  DIR *dp;                            //directory pointer for readdir()
  struct dirent *dir;                 //dir pointer for dirent struct
  struct stat statbuf;                //statbuf for stat struct

  int i = 0;
  char buffer[BUFFER_SIZE];         //buffer for reading in $path
  char *token;                      //token for strtok
  FILE *fpipe;
  char *path = "echo $PATH";        //command for popen()
  char c = 0;
  char *d = "/";                    // "/" for end of tokens


  if(argc == 1){    //no arguments provided -> list executables in current directory

    argv[1] = ".";                                  //setting 1st arg to current dir

    if((dp = opendir(argv[1])) == NULL){
      perror("opendir() error\n");                  //error handling
      return EXIT_FAILURE;
    }

    else{

      dp = opendir(argv[1]);                        //opening directory

      while((dir = readdir(dp)) != NULL){

        if((stat(dir->d_name,&statbuf)) != 0){
          perror("stat() error\n");             //error handling
          return EXIT_FAILURE;
        }

        else{

          stat(dir->d_name,&statbuf);        //returning information to statbuf

          printf("NAME: %s\n",dir->d_name);

          if(S_ISREG(statbuf.st_mode)){        //check if file is regular file AND executable
                printf("REGULAR: YES\n");
                if(statbuf.st_mode & S_IXUSR){
                  printf("EXECUTABLE: YES\n\n");
                }
                else{
                  printf("EXECUTABLE: NO\n\n");
                }
          }
          else{
            printf("REGULAR: NO\n");
            if(statbuf.st_mode & S_IXUSR){
              printf("EXECUTABLE: YES\n\n");
            }
            else{
              printf("EXECUTABLE: NO\n\n");
            }
          }
        }
      }
    }
  }

  if(argc > 1){       //arguments provided -> list executables in specified directory

   if(strchr(argv[1], ':')){            //check if argument contains ":" -> $PATH is used as arg

     char *arr[256];            //array to hold $PATH values

     if(0 == (fpipe = (FILE*) popen(path, "r"))){         //error handling
        perror("popen() failure.\n");
        return EXIT_FAILURE;
      }

      while(fread(&c, sizeof c, 1, fpipe)){
        buffer[i++] = c;                          //assigning $PATH values to buffer
      }
      token = strtok(buffer, DELIM);              //breaking buffer to paths -> 1st path is in token

      int j=0;

      while(token != NULL){
        arr[j++] = append(token, d);            //breaking rest of buffer into paths
        token = strtok(NULL, DELIM);
      }

      for(int i=0; i<j-1; i++){
        if((dp = opendir(arr[i])) == NULL){          //error handling
          perror("opendir() error\n");
          return EXIT_FAILURE;
        }
        else{

          dp = opendir(arr[i]);              //opening directory

          while((dir = readdir(dp)) != NULL){

            if((stat(append(arr[i],dir->d_name),&statbuf)) != 0){
              perror("stat() error\n");                     //error handling
              return EXIT_FAILURE;
            }

            else{

              stat(append(arr[i],dir->d_name),&statbuf);        //returning information to statbuf

              printf("NAME: %s\n",dir->d_name);

              if(S_ISREG(statbuf.st_mode)){        //check if file is regular file AND executable
                    printf("REGULAR: YES\n");
                    if(statbuf.st_mode & S_IXUSR){
                      printf("EXECUTABLE: YES\n\n");
                    }
                    else{
                      printf("EXECUTABLE: NO\n\n");
                    }
              }
              else{
                printf("REGULAR: NO\n");
                if(statbuf.st_mode & S_IXUSR){
                  printf("EXECUTABLE: YES\n\n");
                }
                else{
                  printf("EXECUTABLE: NO\n\n");
                }
              }
            }
          }
        }
      }
   }
   else{                //if argument is not $PATH

    for(int i=1; i<argc; i++){
      dp = opendir(argv[i]);
      if(dp != NULL){          //error handling
        while((dir=readdir(dp)) != NULL){
          if(stat(append(argv[i],dir->d_name),&statbuf) != 0){
            perror("stat() error\n");                     //error handling
            //return EXIT_FAILURE;
          }

          else{
              //stat(append(argv[i],dir->d_name),&statbuf);        //returning information to statbuf

              printf("NAME: %s\n",dir->d_name);

              if(S_ISREG(statbuf.st_mode)){        //check if file is regular file AND executable
                    printf("REGULAR: YES\n");
                    if(statbuf.st_mode & S_IXUSR){
                      printf("EXECUTABLE: YES\n\n");
                    }
                    else{
                      printf("EXECUTABLE: NO\n\n");
                    }
              }
              else{
                printf("REGULAR: NO\n");
                if(statbuf.st_mode & S_IXUSR){
                  printf("EXECUTABLE: YES\n\n");
                }
                else{
                  printf("EXECUTABLE: NO\n\n");
                }
              }
            }
        }
      }
      else{
        perror("opendir() error\n");
        return EXIT_FAILURE;

      }
    }
   }
  }

  closedir(dp);                   //closing directory

  return EXIT_SUCCESS;
}
