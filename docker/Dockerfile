# Dockerfile (project root)
ARG BASE_IMAGE
FROM ${BASE_IMAGE:-eclipse-temurin:25-jre}

# install curl for debugging
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# run as non-root
RUN groupadd -r app && useradd -r -g app app
WORKDIR /app

# copy startup script and app jar file
COPY docker/* /app/
COPY build/libs/*.jar /app/
COPY ../lib/applicationinsights.json /app/

USER app
ENTRYPOINT ["/bin/sh","./startup.sh"]