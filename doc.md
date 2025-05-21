Documentación Técnica del Proyecto MAT
Tabla de Contenidos
Descripción General del Sistema
Especificación Técnica de Componentes
2.1. AI Client
2.2. MCP Server (MAT Control Plane Server)
2.3. MAT Context (Recursos y Herramientas Externas)
Diagramas UML
3.1. Diagrama de Casos de Uso
3.2. Diagrama de Componentes
3.3. Diagrama de Secuencia (Ejemplo: Creación de Pruebas)
3.4. Diagrama de Actividades (Ejemplo: Onboarding)
Esquema de Arquitectura del Sistema
Flujo de Datos y Procesos
5.1. Interacción General del Usuario
5.2. Flujo Detallado de Comandos Específicos
Detalles de Implementación
6.1. Tecnologías y Frameworks
6.2. Patrones Utilizados
Requisitos No Funcionales
7.1. Escalabilidad
7.2. Seguridad
7.3. Rendimiento
7.4. Mantenibilidad
Guías para Desarrolladores y Usuarios Técnicos
8.1. Guía de Interacción con el Agente MAT
8.2. Configuración del Entorno
1. Descripción General del Sistema
El proyecto MAT (Mentor & Automation Tool) es un sistema diseñado para asistir y automatizar diversas tareas dentro del ciclo de vida del desarrollo y pruebas de software. Su objetivo principal es agilizar los procesos, mejorar la productividad de los equipos de desarrollo y QA, y centralizar la gestión de tareas comunes.
El sistema se compone de una interfaz de cliente basada en IA (AI Client) que permite a los usuarios interactuar mediante comandos de lenguaje natural o predefinidos. Estos comandos son procesados por un servidor central (MCP Server - MAT Control Plane Server), el cual orquesta las acciones necesarias, interactuando con herramientas externas como Jira, Github, Jenkins y Confluence.
Las funcionalidades clave identificadas incluyen:
Automatic Onboarding: Simulación de la creación de tickets y repositorios para nuevos proyectos o miembros del equipo (ej. capgineers).
User Story / Test Creation: Creación de historias de usuario y pruebas, potencialmente vinculadas a épicas existentes en Jira.
MAT Coding & Integration: Asistencia en la integración de código, como la organización de scripts de Selenium utilizando el patrón PageObject y la creación de Pull Requests (PR) en Github.
Test Plan Definition & Prioritization: Ayuda en la organización de planes de pruebas, sugiriendo agrupaciones basadas en diferentes criterios como procesos de compra, gestión de usuarios, asistencia técnica, o por criticidad y riesgo (zonas con más bugs recientes).
El sistema busca reducir la carga manual en tareas repetitivas y facilitar una gestión más eficiente y contextualizada de los artefactos de desarrollo y pruebas.
2. Especificación Técnica de Componentes
2.1. AI Client
Descripción: Es la interfaz a través de la cual los usuarios interactúan con el sistema MAT. Interpreta los comandos del usuario y los envía al MCP Server para su procesamiento. Se presenta como un asistente conversacional (ej. PS D:\Applications\hackaton\latest> node .\index.js).
Responsabilidades:
Capturar la entrada del usuario (comandos).
Comunicarse con el MCP Server.
Presentar las respuestas y resultados del MCP Server al usuario.
Tecnologías Potenciales: Podría ser una aplicación de línea de comandos (CLI), un chatbot integrado en plataformas de mensajería, o una interfaz web. La imagen Agent.png sugiere una CLI basada en Node.js.
2.2. MCP Server (MAT Control Plane Server)
Descripción: Es el cerebro del sistema MAT. Recibe las solicitudes del AI Client, las procesa, y coordina las interacciones con las herramientas externas definidas en el MAT Context.
Responsabilidades:
Autenticar y autorizar las solicitudes del AI Client.
Interpretar la lógica de negocio para cada comando.
Gestionar el flujo de trabajo para funcionalidades como "Automatic Onboarding", "User story creation", "MAT coding & integration", y "TEST PLAN DEFINITION & PRIORITIZATION".
Interactuar con las APIs de las herramientas externas (Jira, Github, etc.).
Mantener el estado o contexto de la interacción si es necesario.
Devolver respuestas y resultados al AI Client.
Tecnologías Potenciales: Aplicación backend desarrollada en lenguajes como Node.js, Python, Java, Go, etc., utilizando arquitecturas RESTful o gRPC para la comunicación con el AI Client y las herramientas externas.
2.3. MAT Context (Recursos y Herramientas Externas)
Descripción: Conjunto de herramientas y sistemas de terceros con los que el MCP Server interactúa para llevar a cabo sus funciones.
Componentes Identificados:
Jira: Para la gestión de proyectos, creación de tickets (historias de usuario, épicas, tareas de prueba).
Github: Para la gestión de código fuente, creación de repositorios y Pull Requests.
Jenkins: Potencialmente para la automatización de builds, despliegues o ejecución de pruebas (aunque no se muestra explícitamente una interacción en los prompts, es una herramienta común en este contexto).
Confluence: Potencialmente para la gestión de documentación (similar a Jenkins, no hay interacción explícita mostrada pero forma parte del contexto).
Interacción: Se realiza a través de las APIs proporcionadas por cada una de estas herramientas. El MCP Server debe manejar la autenticación y autorización necesarias para operar con estas APIs.
3. Diagramas UML
3.1. Diagrama de Casos de Uso
Este diagrama ilustra las principales funcionalidades (casos de uso) que el sistema MAT ofrece a sus usuarios (actores).
graph TD
    actor Usuario as "Usuario (Desarrollador/QA)"

    subgraph Sistema MAT
        UC1[Realizar Onboarding de Proyecto]
        UC2[Crear Pruebas/Stories]
        UC3[Integrar Pruebas Locales]
        UC4[Organizar Plan de Pruebas]
        UC5[Finalizar Sesión]
    end

    Usuario -- Petición de Onboarding --> UC1
    Usuario -- Solicita creación de pruebas/stories --> UC2
    Usuario -- Envía script de Selenium --> UC3
    Usuario -- Solicita organizar pruebas --> UC4
    Usuario -- Comando 'salir' --> UC5

    UC1 -- Interactúa con --> Jira
    UC1 -- Interactúa con --> Github
    UC2 -- Interactúa con --> Jira
    UC3 -- Interactúa con --> Github
    UC4 -- Interactúa con --> Jira

    classDef actor fill:#D6EAF8,stroke:#3498DB,stroke-width:2px;
    classDef usecase fill:#E8F8F5,stroke:#1ABC9C,stroke-width:2px;
    classDef external fill:#FCF3CF,stroke:#F1C40F,stroke-width:2px;

    class Usuario actor;
    class Jira, Github external;
    class UC1,UC2,UC3,UC4,UC5 usecase;


3.2. Diagrama de Componentes
Este diagrama muestra los principales componentes del sistema y sus interdependencias, basado en la imagen MCP MAT.png.
graph TD
    subgraph "Usuario"
        AIClient["AI Client"]
    end

    subgraph "Sistema MAT Central"
        MCPServer["MCP Server"]
    end

    subgraph "Herramientas Externas (MAT Context)"
        Jira["Jira API"]
        Github["Github API"]
        Jenkins["Jenkins API (potencial)"]
        Confluence["Confluence API (potencial)"]
        Selenium["Selenium (para scripts de prueba)"]
    end

    AIClient -- Envía Comandos/Datos --> MCPServer
    MCPServer -- Recibe Comandos/Datos --> AIClient
    MCPServer -- API Calls --> Jira
    MCPServer -- API Calls --> Github
    MCPServer -- API Calls --> Jenkins
    MCPServer -- API Calls --> Confluence
    MCPServer -- Procesa/Organiza --> Selenium

    %% Funcionalidades del MCP Server
    MCPServer --> F1["Automatic Onboarding"]
    MCPServer --> F2["User Story Creation"]
    MCPServer --> F3["MAT Coding & Integration"]
    MCPServer --> F4["Test Plan Definition & Prioritization"]

    style AIClient fill:#EBF5FB,stroke:#2980B9
    style MCPServer fill:#E8F6F3,stroke:#16A085
    style Jira fill:#FEF9E7,stroke:#F39C12
    style Github fill:#FEF9E7,stroke:#F39C12
    style Jenkins fill:#FEF9E7,stroke:#F39C12
    style Confluence fill:#FEF9E7,stroke:#F39C12
    style Selenium fill:#FDEDEC,stroke:#E74C3C
    style F1 fill:#F4ECF7,stroke:#8E44AD
    style F2 fill:#F4ECF7,stroke:#8E44AD
    style F3 fill:#F4ECF7,stroke:#8E44AD
    style F4 fill:#F4ECF7,stroke:#8E44AD


3.3. Diagrama de Secuencia (Ejemplo: Creación de Pruebas e Integración)
Este diagrama muestra la secuencia de interacciones para el caso de uso donde un usuario quiere crear pruebas y luego integrar un script local.
sequenceDiagram
    participant Usuario
    participant AIClient as "AI Client"
    participant MCPServer as "MCP Server"
    participant Jira
    participant Github

    Usuario->>+AIClient: Comando: "crear pruebas"
    AIClient->>+MCPServer: Solicitar creación de pruebas/stories (posiblemente con contexto)
    MCPServer-->>-AIClient: Solicitar más contexto (ej. "¿esta historia relacionada con Épica 32?")
    AIClient-->>-Usuario: "¿Quieres que te relacione con la Épica 32?"
    Usuario->>+AIClient: Confirmación: "si por fa"
    AIClient->>+MCPServer: Confirmación y detalles (ej. relacionar con Épica 32)
    MCPServer->>+Jira: Crear Ticket de Prueba/Story (vinculado a Épica 32)
    Jira-->>-MCPServer: Ticket Creado (ID: CSTD-CTTI-...)
    MCPServer-->>-AIClient: Ticket creado con éxito (URL: ...)
    AIClient-->>-Usuario: "Ticket creado con éxito: [URL]"

    Usuario->>+AIClient: Comando: "pruebas en local" (implica integrar script)
    AIClient->>+MCPServer: Solicitar integración de pruebas locales
    MCPServer-->>-AIClient: Solicitar script de Selenium
    AIClient-->>-Usuario: "Pásame el script de Selenium"
    Usuario->>+AIClient: Envía script: "aqui tienes D://pruebas.side"
    AIClient->>+MCPServer: Transmitir script de Selenium (pruebas.side)
    MCPServer->>MCPServer: Organizar código (ej. PageObject)
    MCPServer->>+Github: Crear/Actualizar Archivos de Prueba
    MCPServer->>+Github: Crear Pull Request (PR)
    Github-->>-MCPServer: PR Creado (URL: ...)
    MCPServer-->>-AIClient: Código organizado y PR creado (URL: ...)
    AIClient-->>-Usuario: "Te he subido la PR aquí tienes el link: [URL]"


3.4. Diagrama de Actividades (Ejemplo: Onboarding)
Este diagrama muestra el flujo de actividades para el proceso de "Automatic Onboarding".
graph TD
    A[Inicio: Usuario envía comando 'onboarding'] --> B{¿Información de proyecto completa?};
    B -- No --> C[AI Client solicita detalles del proyecto (ej. nombre)];
    C --> D[Usuario proporciona detalles];
    D --> E[AI Client envía solicitud de onboarding al MCP Server];
    B -- Sí --> E;
    E --> F[MCP Server: Inicia proceso de onboarding];
    F --> G[MCP Server: Crea ticket en Jira];
    G -- Éxito --> H[Jira devuelve ID de ticket];
    H --> I[MCP Server: Crea repositorio en Github];
    I -- Éxito --> J[Github devuelve URL de repositorio];
    J --> K[MCP Server: Notifica al AI Client el éxito y los enlaces];
    K --> L[AI Client: Muestra al usuario los enlaces de Jira y Github];
    L --> M[Fin];
    G -- Fallo --> N[MCP Server: Registra error y notifica];
    N --> K;
    I -- Fallo --> O[MCP Server: Registra error y notifica];
    O --> K;

    style A fill:#A9DFBF,stroke:#27AE60,stroke-width:2px
    style M fill:#A9DFBF,stroke:#27AE60,stroke-width:2px
    style B fill:#F9E79F,stroke:#F1C40F,stroke-width:2px
    style F fill:#AED6F1,stroke:#3498DB,stroke-width:2px
    style G fill:#AED6F1,stroke:#3498DB,stroke-width:2px
    style I fill:#AED6F1,stroke:#3498DB,stroke-width:2px
    style K fill:#AED6F1,stroke:#3498DB,stroke-width:2px
    style L fill:#D2B4DE,stroke:#8E44AD,stroke-width:2px
    style N fill:#F5B7B1,stroke:#C0392B,stroke-width:2px
    style O fill:#F5B7B1,stroke:#C0392B,stroke-width:2px


4. Esquema de Arquitectura del Sistema
El sistema MAT adopta una arquitectura cliente-servidor, donde el AI Client actúa como la interfaz de usuario y el MCP Server como el backend que centraliza la lógica de negocio y la orquestación de servicios externos.
graph LR
    subgraph "Entorno de Usuario"
        UserInteraction["Usuario (Desarrollador/QA)"] --- AIC["AI Client (Node.js CLI)"]
    end

    subgraph "Núcleo del Sistema MAT"
        AIC -- HTTP/S u otro protocolo --> MCPS["MCP Server"]
        MCPS -- Lógica de Negocio --> F1["Módulo Onboarding"]
        MCPS -- Lógica de Negocio --> F2["Módulo Creación User Stories/Pruebas"]
        MCPS -- Lógica de Negocio --> F3["Módulo Integración Código (Selenium, PageObject)"]
        MCPS -- Lógica de Negocio --> F4["Módulo Definición/Priorización Plan de Pruebas"]
    end

    subgraph "Servicios Externos (MAT Context)"
        F1 & F2 & F4 -- API REST/GraphQL --> Jira["Jira"]
        F1 & F3 -- API REST/GraphQL --> Github["Github"]
        %% Se asume que Jenkins y Confluence podrían ser integrados de manera similar
        MCPS -. API Calls .-> Jenkins["Jenkins (potencial)"]
        MCPS -. API Calls .-> Confluence["Confluence (potencial)"]
    end

    style UserInteraction fill:#D6EAF8,stroke:#3498DB
    style AIC fill:#EBF5FB,stroke:#2980B9
    style MCPS fill:#E8F6F3,stroke:#16A085
    style F1 fill:#F4ECF7,stroke:#8E44AD
    style F2 fill:#F4ECF7,stroke:#8E44AD
    style F3 fill:#F4ECF7,stroke:#8E44AD
    style F4 fill:#F4ECF7,stroke:#8E44AD
    style Jira fill:#FEF9E7,stroke:#F39C12
    style Github fill:#FEF9E7,stroke:#F39C12
    style Jenkins fill:#FDEDEC,stroke:#E74C3C
    style Confluence fill:#FDEDEC,stroke:#E74C3C


Descripción de la Arquitectura:
AI Client: Es el punto de entrada para el usuario. Responsable de capturar las intenciones del usuario (comandos) y enviarlas al MCP Server. También recibe y muestra las respuestas del servidor.
MCP Server: El componente central que aloja la lógica de negocio. Se comunica con el AI Client (probablemente a través de una API RESTful o similar) y orquesta las acciones con los servicios externos. Contiene módulos especializados para cada funcionalidad principal.
MAT Context (Recursos y Herramientas): Son los servicios de terceros (Jira, Github, etc.) que el MCP Server utiliza para realizar tareas específicas como la creación de tickets, gestión de código, etc. La comunicación se realiza a través de sus respectivas APIs.
Este diseño promueve la separación de preocupaciones, permitiendo que el AI Client se enfoque en la interacción con el usuario y el MCP Server en la lógica de negocio y la integración con servicios externos.
5. Flujo de Datos y Procesos
5.1. Interacción General del Usuario
El usuario inicia el AI Client (ej. node .\index.js).
El AI Client se presenta y espera un comando del usuario.
El usuario introduce un comando (ej. onboarding, crear las pruebas).
El AI Client envía el comando y cualquier dato asociado al MCP Server.
