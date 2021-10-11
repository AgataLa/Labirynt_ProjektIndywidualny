FROM postgres:latest
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD labirynt
ENV POSTGRES_DB postgres
RUN localedef -i pl_PL -c -f UTF-8 -A /usr/share/locale/locale.alias pl_PL.UTF-8
ENV LANG pl_PL.UTF-8
COPY initdb /docker-entrypoint-initdb.d
