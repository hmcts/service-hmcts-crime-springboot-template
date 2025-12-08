# Makefile for Docker Compose services

.PHONY: build up down restart logs

build:
	@echo "Building Gradle JAR..."
	gradle clean bootJar
	@echo "Building Docker image..."
	docker-compose build

up:
	docker-compose --env-file local.env up -d

down:
	docker-compose down

restart: down up

logs:
	docker-compose logs -f
