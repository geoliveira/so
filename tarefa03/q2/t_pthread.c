#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_LENGTH 94

unsigned long long int shared_struct[MAX_LENGTH]; // array shared by the thread

unsigned long long int* fib_thread(int n) 
{
    if (n == 0 || n == 1)
    {
        pthread_exit((unsigned long long int*)shared_struct[n]);
    } 
    else {
        printf("%d\n", n);
        unsigned long long int x_n1, x_n2;
        pthread_t tid_n1, tid_n2;
        
        pthread_create(&tid_n1, NULL, (void*)fib_thread, (void*)(n-1));
        pthread_create(&tid_n2, NULL, (void*)fib_thread, (void*)(n-2));
        pthread_join(tid_n1, (void*)&x_n1);
        pthread_join(tid_n2, (void*)&x_n2); 

        shared_struct[n] = x_n1 + x_n2;

        pthread_exit((unsigned long long int*)shared_struct[n]);
    }
}

int main(int argc, char *argv[])
{
    pthread_t tid; // the thread identifier
    pthread_attr_t attr; // set of attributes for the thread

    if (argc != 2) {
        fprintf(stderr,"usage: ./p_thread <position fibonacci term>\n");
        return -1;
    } else if (atoi(argv[1]) <= 0 || atoi(argv[1]) > 94) {
        fprintf(stderr,"Argument %d must be >= 1 and <= 94\n", atoi(argv[1]));
        return -1;
    }
    
    int n = atoi(argv[1]);

    for(int i=0; i < MAX_LENGTH; ++i) shared_struct[i] = (i == 0 || i == 1) ? i : -1;

    pthread_attr_init(&attr); // get the default attributes 
    pthread_create(&tid, &attr, (void*)fib_thread, (void*)n); // create the thread 
    pthread_join(tid, NULL); // now wait for the thread to exit 
    
    for(int i=0; i < n; ++i)
        printf("fib(%d) = %lld\n", i, shared_struct[i]);
}
