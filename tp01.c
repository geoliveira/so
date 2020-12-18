#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <unistd.h>
#include <sys/wait.h>

unsigned long long int fib(int n) {
    if(n < 0) return -1;
    return ( (1/sqrt(5)) * ( pow((1+sqrt(5))/2, n) - pow((1-sqrt(5))/2, n) ) );
} 

int main(int argc, char const *argv[])
{
    if (argc == 2)
    {
        printf("Item a)\nA posicao %d da sequencia sera mostrada!\nProcesso principal: %d\n", atoi(argv[1]), getpid());
        printf("fib(%d)=%llu\n", atoi(argv[1]), fib(atoi(argv[1])-1));
        return 1;
    } if (argc == 3) {
        int pid_a, pid_b, tam_interv, inicio = atoi(argv[1])-1, final = atoi(argv[2])-1;
        tam_interv = final-inicio+1;
        
        printf("Item b)\nA sequencia de mostrada e da posicao %d ate %d. No total de %d numeros!\n[OBS: LIMITE DA SEQUENCIA EH A POSICAO 94]\n\nProcesso principal: %d\n", inicio+1, final+1, tam_interv, getpid());

        pid_a = fork();
        if (pid_a == 0) 
        {
            printf("Eu sou filho %d do meu pai %d.\n",getpid(),getppid()) ;
            for(int i = inicio; i <= final; i++)
            {
                if(i == inicio+(tam_interv/2))
                {
                    pid_b = fork();
                    if(pid_b != 0)
                    {
                        wait(&pid_b);
                        break;
                    }
                    printf("Eu sou filho %d do meu pai %d.\n",getpid(),getppid()) ;
                }
                printf(" pid=%d. fib(%d)=%llu\n", getpid(), i+1, fib(i));
                sleep(2);
            }
        } else {
            wait(&pid_a);
        }
        printf("Eu, %d, estou capando o gato!\n", getpid());
        return 1;
    } 
    printf("Item a) Eh necessario apenas um paramento\nItem b) Somente com dois parametros\nEntradas que sejam inteiros positivos!\n");
    return 0;
}