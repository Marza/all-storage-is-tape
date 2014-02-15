#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 

int main(int argc, char *argv[])
{
    printf("Started writer!\n");
    char data[1024];
    for (int i = 0; i < 1024; i++) {
        data[i] = 97 + rand() % 26;
    }

    for (int i = 0; i < 1000; i++) {
        char filename[20];
        sprintf(filename, "client%04d.txt", i);

        FILE *f = fopen(filename, "wb");
        fwrite(data, sizeof(char), sizeof(data), f);
        fclose(f);
    }
    
    return 0;
}