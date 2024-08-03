# SpringAIWithReact

Using「Spring AI with OpenAI」
from https://spring.io/projects/spring-ai
and Create Input form with React

Contains a web service that will accept HTTP GET requests at
`http://localhost:8080/ai`.
The response to the request is from the OpenAI ChatGPT Service.

## Prerequisites
Before using the AI commands, make sure you have a developer token from OpenAI.

Create an account at [OpenAI Signup](https://platform.openai.com/signup) and generate the token at [API Keys](https://platform.openai.com/account/api-keys).

Exporting an environment variable is one way to set that configuration property.
```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

## Building and running
```
./mvnw package
```

## Access the endpoint
localhost:8080/ai

 
