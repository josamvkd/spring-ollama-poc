# Spring Ollama POC

A Proof of Concept (POC) exploring the integration of **Spring Boot with Ollama** to build AI-powered Java applications using locally hosted Large Language Models (LLMs), featuring RESTful APIs, prompt handling, and AI-generated responses.

This project demonstrates how to integrate locally running AI models into Java backend applications using **Spring AI**, without depending on external cloud-based AI APIs.

## 🚀 Features

The POC explores the following capabilities:

- **Ollama Integration:** Connect Spring Boot applications to locally hosted LLMs.
- **Spring AI Framework:** Simplify LLM integration using Spring AI abstractions.
- **RESTful APIs:** Expose AI-powered functionality through REST endpoints.
- **Prompt Handling:** Accept user prompts and generate contextual responses.
- **Local AI Inference:** Run supported LLMs locally without requiring cloud API keys.
- **Model Configuration:** Configure and switch between supported Ollama models.
- **Extensible Architecture:** Establish a foundation for AI-powered Java microservices.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 17+ | Backend development |
| Spring Boot | Application framework |
| Spring AI | LLM integration framework |
| Ollama | Local LLM runtime |
| Llama 3.2 | Example language model |
| Maven | Build and dependency management |
| REST APIs | Client-server communication |
| Postman | API testing |

## 📋 Prerequisites

Before running the application, ensure the following are available:

- JDK 17 or higher, compatible with your Spring Boot version
- Apache Maven
- IntelliJ IDEA or Spring Tool Suite
- Ollama installed on your machine
- A locally downloaded Ollama model
- Sufficient system RAM for the selected model

**Note:** An OpenAI API key is not required for local Ollama inference. Internet access is generally required for initial installation and model downloads.

## ⚙️ Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/spring-ollama-poc.git
cd spring-ollama-poc
```

Replace `<your-username>` with your GitHub username.

### 2. Install Ollama

Download and install Ollama from the official website:

https://ollama.com/download

Verify the installation:

```bash
ollama --version
```

### 3. Download a Language Model

Download the Llama 3.2 model:

```bash
ollama pull llama3.2
```

Verify that the model is available:

```bash
ollama list
```

You can also test the model interactively:

```bash
ollama run llama3.2
```

Example prompt:

```text
Explain Spring Boot in simple terms.
```

To exit the interactive session:

```text
/bye
```

### 4. Start the Ollama Server

Ensure Ollama is running locally.

If Ollama is not already running as a background service, start it using:

```bash
ollama serve
```

By default, the Ollama server is accessible at:

```text
http://localhost:11434
```

Verify server availability:

```bash
curl http://localhost:11434/api/tags
```

The response should contain information about the locally installed models.

### 5. Add Spring AI Dependency

For a Maven-based Spring AI application, add the Ollama starter dependency:

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-ollama</artifactId>
</dependency>
```

Configure the appropriate Spring AI BOM in `pom.xml` to manage compatible dependency versions.

### 6. Configure application.properties

Example configuration:

```properties
spring.application.name=spring-ollama-poc

server.port=8080

# Ollama Server
spring.ai.ollama.base-url=http://localhost:11434

# Ollama Model
spring.ai.ollama.chat.options.model=llama3.2

# Model Temperature
spring.ai.ollama.chat.options.temperature=0.7
```

**Note:** Configuration properties can differ between Spring AI versions. For example, newer versions may use `spring.ai.ollama.chat.model` instead of `spring.ai.ollama.chat.options.model`.

Use the property supported by your project's Spring AI version.

### 7. Build the Application

```bash
mvn clean install
```

### 8. Run the Application

```bash
mvn spring-boot:run
```

The example application starts at:

```text
http://localhost:8080
```

## 🏗️ Architecture Overview

The POC follows a simple request-response architecture:

```text
               Client / Postman
                      |
                      | HTTP Request
                      v
             Spring Boot REST API
                      |
                      v
                 Controller
                      |
                      v
             Spring AI ChatClient
                      |
                      v
              Ollama Local API
             (localhost:11434)
                      |
                      v
             Local LLM (Llama 3.2)
                      |
                      v
               AI Response
                      |
                      v
                REST Client
```

### Request Processing Flow

1. A client submits a prompt through a REST API.
2. The Spring Boot controller receives the request.
3. Spring AI processes the prompt through `ChatClient`.
4. Ollama forwards the prompt to the configured local LLM.
5. The model generates an AI response.
6. Spring Boot returns the generated response to the client.

## 🔌 REST API Example

The following endpoint illustrates a possible REST API implementation.

### Generate AI Response

**HTTP Method:** GET

**Endpoint:**

```http
GET /api/ai/chat
```

**Request:**

```bash
curl -G "http://localhost:8080/api/ai/chat" \
  --data-urlencode "message=Explain microservices architecture"
```

**Example Response:**

```text
Microservices architecture is a software design
approach where an application is divided into
smaller, independently deployable services.

Each service focuses on a specific business
capability and communicates with other services
through APIs or messaging systems.
```

The response content can vary depending on the model and prompt.

## 💻 Sample Spring Boot Implementation

The following is a reference implementation using Spring AI's `ChatClient`.

```java
package com.example.springollamapoc.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
```

### Implementation Details

- `ChatClient.Builder` is configured through Spring AI auto-configuration.
- `prompt()` creates a new AI prompt request.
- `user(message)` supplies the user input.
- `call()` sends the request to the configured Ollama model.
- `content()` retrieves the generated response text.

This design separates the REST interface from the underlying model integration.

## 📁 Example Project Structure

```text
spring-ollama-poc/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springollamapoc/
│   │   │       ├── controller/
│   │   │       │   └── OllamaController.java
│   │   │       └── SpringOllamaPocApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── pom.xml
└── README.md
```

The structure is illustrative and should be updated to reflect the actual repository.

## 🧠 Supported Language Models

Ollama supports a variety of locally executable language models.

Examples include:

| Model | Example Command |
|---|---|
| Llama 3.2 | `ollama pull llama3.2` |
| Mistral | `ollama pull mistral` |
| Gemma 3 | `ollama pull gemma3` |
| Qwen 2.5 | `ollama pull qwen2.5` |
| Phi-3 | `ollama pull phi3` |

Model availability, hardware requirements, and performance vary by model.

To switch models, download the desired model and update the Spring AI configuration to reference its installed name.

## 🔒 Privacy and Security

Running LLMs locally provides several potential advantages:

- **Local Processing:** Prompts can be processed without sending them to an external cloud LLM.
- **Data Control:** Application data can remain within the local environment.
- **No Cloud API Key:** Local Ollama inference does not require an external AI API key.
- **Development Flexibility:** Experiment with different models and configurations.

For production environments, additional security measures are recommended:

- Avoid exposing the Ollama API directly to the public internet.
- Apply authentication and authorization to REST endpoints.
- Validate and limit incoming requests.
- Configure appropriate network restrictions.
- Protect sensitive information in logs.
- Monitor CPU, GPU, and memory consumption.

## ⚡ Performance Considerations

Local LLM inference performance depends on several factors:

- Model architecture and parameter count
- Available RAM and GPU memory
- CPU and GPU capabilities
- Prompt size and context length
- Number of concurrent requests
- Model quantization and runtime configuration

Smaller models can be useful for initial experimentation on development machines.

For production workloads, evaluate latency, throughput, resource utilization, and response quality before selecting a model.

## 🧪 API Testing

The REST endpoint can be tested using Postman or cURL.

Example cURL request:

```bash
curl -G "http://localhost:8080/api/ai/chat" \
  --data-urlencode "message=What is dependency injection in Spring?"
```

Expected behavior:

1. The application receives the prompt.
2. Spring AI communicates with the local Ollama service.
3. The configured language model generates a response.
4. The generated text is returned to the REST client.

## 🛠️ Troubleshooting

### Ollama Connection Refused

Verify that the Ollama server is running:

```bash
ollama serve
```

If Ollama is already running as a service, starting another instance may cause a port conflict.

### Model Not Found

Verify the installed models:

```bash
ollama list
```

Download the configured model if necessary:

```bash
ollama pull llama3.2
```

### Slow AI Responses

Consider using a smaller model, reducing prompt size, or enabling supported GPU acceleration.

### Spring Boot Application Fails to Start

Check:

- Java and Spring Boot version compatibility
- Spring AI dependency compatibility
- Maven dependency resolution
- Ollama service availability
- Model configuration properties

## 🔮 Future Enhancements

Potential improvements to this POC include:

- **Conversational AI:** Maintain context across multiple user interactions.
- **Streaming Responses:** Deliver AI-generated output incrementally.
- **Retrieval-Augmented Generation (RAG):** Generate responses using external knowledge sources.
- **Vector Databases:** Integrate PostgreSQL with pgvector, Redis, or other supported vector stores.
- **Document Processing:** Analyze PDFs and other documents.
- **Structured Responses:** Generate JSON and application-specific structured output.
- **Function Calling:** Connect local LLMs to application tools where supported.
- **Docker Integration:** Containerize Spring Boot and Ollama services.
- **Observability:** Monitor AI request latency, resource usage, and failures.
- **Microservices Integration:** Expose AI capabilities to distributed backend services.
- **Local vs Cloud AI Comparison:** Evaluate performance, cost, and deployment trade-offs.

## 🎯 Learning Objectives

The primary objectives of this POC are to:

- Understand how locally hosted LLMs work.
- Integrate Ollama with Java and Spring Boot.
- Explore Spring AI's `ChatClient` abstraction.
- Build RESTful APIs backed by local language models.
- Understand prompt handling and AI response generation.
- Experiment with different open-weight models.
- Explore AI integration patterns for Java microservices.
- Establish a foundation for enterprise Generative AI applications.

## 📚 References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring AI Ollama Integration](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html)
- [Ollama Official Website](https://ollama.com/)
- [Ollama GitHub Repository](https://github.com/ollama/ollama)
- [Ollama Model Library](https://ollama.com/library)

## 📄 License

This project is intended for educational, experimental, and proof-of-concept purposes. No open-source license is implied unless a LICENSE file is added.

---

**Spring Ollama POC** — Exploring local Generative AI integration using Java, Spring Boot, Spring AI, and Ollama.
