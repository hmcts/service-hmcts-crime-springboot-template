# Docker base image - note that this is currently overwritten by azure pipelines
FROM ${BASE_IMAGE:-eclipse-temurin:21-jdk}

# run as non-root ... group and user "app"
RUN groupadd -r app && useradd -r -g app app
WORKDIR /app

# ---- Dependencies ----
RUN apt-get update \
    && apt-get install -y curl \
    && rm -rf /var/lib/apt/lists/* \

# copy startup script and app jar file
COPY docker/* /app/
COPY build/libs/*.jar /app/
COPY lib/applicationinsights.json /app/

USER app
ENTRYPOINT ["/bin/sh","./startup.sh"]