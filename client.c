#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

int main()
{
    int sock, bytes_recieved;
    char send_data[1024], recv_data[1024];
    struct hostent *host;
    struct sockaddr_in server_addr;

    host = gethostbyname("127.0.0.1");
    sock = socket(AF_INET, SOCK_STREAM, 0);

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(5000);
    server_addr.sin_addr = *((struct in_addr *)host->h_addr);
    bzero(&(server_addr.sin_zero), 8);

    connect(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr));

    int i = 0;
    for(i = 0; i < 1000; i++)
    {
        // bytes_recieved = recv(sock, recv_data, 1024, 0);
        // recv_data[bytes_recieved] = '\0';
        // printf("\nRecieved data = %s " , recv_data);

        send(sock, send_data, 1024, 0);
    }

    return 0;
} 