FROM postgres

RUN \
  bash -c 'apt-mark hold postgresql-common' && \
  bash -c 'apt-get -qq update && apt-get -y upgrade && apt-get -y autoclean && apt-get -y autoremove' && \
  bash -c 'DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget tar'

ENV DB_NAME POSTGRES_DB
ENV DB_USER POSTGRES_USER
ENV DB_PASSWD POSTGRES_PASSWORD
ENV DB_TABLE POSTGRES_TABLE

RUN mkdir -p /docker-entrypoint-initdb.d
ADD scripts/db-init.sh /docker-entrypoint-initdb.d/
RUN chmod 755 /docker-entrypoint-initdb.d/db-init.sh
