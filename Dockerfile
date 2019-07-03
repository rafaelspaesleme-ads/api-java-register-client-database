#imagem do pacote de execucao da apicacao em questao
FROM openjdk:8-jdk-slim
FROM maven:3.2-jdk-8

#update packages

ENV DB_NAME DATABASE_DB
ENV DB_USER DATABASE_USER
ENV DB_PASSWORD DATABASE_PASSWORD
ENV DB_HOST DATABASE_HOST
ENV DB_PORT DATABASE_PORT

#Criando diretorio da aplicação
RUN mkdir /app

#Criando diretorio para o microservice (aplicacao)
WORKDIR /app

#Copie o .jar no diretório de trabalho
COPY target/webclient-0.0.1-SNAPSHOT.jar /app

#Configuracao de porta
#EXPOSE 8081:8081

#Copiando entrypoint para pasta raiz
#COPY devops/app/scripts/entrypoint.sh /app

#Executando permissão de execução
#RUN chmod +x /app/entrypoint.sh

#Executando entrypoint
#RUN bash -c '/app/entrypoint.sh'

#Comando que será executado assim que executarmos o contêiner
CMD ["java","-jar","webclient-0.0.1-SNAPSHOT.jar"]