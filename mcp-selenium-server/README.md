# MCP Selenium Server

A Model Context Protocol (MCP) Server that exposes Selenium WebDriver tools for web browser automation. This server allows AI clients to execute standardized browser actions for automated web testing.

## Descripción del Proyecto

El MCP Selenium Server actúa como el "cerebro" del sistema de automatización, proporcionando una interfaz estandarizada para que los clientes AI puedan ejecutar acciones de navegador mediante Selenium WebDriver. Este servidor implementa el patrón FastMCP y se integra perfectamente con el proyecto MAT (Mentor & Automation Tool).

## Características

- ✅ 10 herramientas (tools) de Selenium completamente funcionales
- ✅ Soporte para Chrome y Firefox
- ✅ Integración con Selenium Grid
- ✅ Gestión automática de WebDrivers
- ✅ Capturas de pantalla automáticas
- ✅ Manejo robusto de errores

## Requisitos Previos

### Software Requerido

- **Python 3.8+**
- **pip** (gestor de paquetes de Python)

### Opcional (para Selenium Grid)

- **Docker** y **docker-compose** (para ejecutar Selenium Grid)
- Selenium Grid configurado (ver sección de Selenium Grid más abajo)

## Instalación

1. Navegar al directorio del servidor:

```bash
cd mcp-selenium-server
```

2. Crear un entorno virtual (recomendado):

```bash
python3 -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
```

3. Instalar dependencias:

```bash
pip install -r requirements.txt
```

## Ejecución

### Modo Estándar (Navegador Local)

Para ejecutar el servidor MCP con un navegador local:

```bash
python server.py
```

### Modo con Selenium Grid

Si deseas usar Selenium Grid (recomendado para pruebas distribuidas), primero inicia el Grid:

```bash
# Desde el directorio raíz del proyecto
docker-compose -f docker-compose-v3.yml up -d --scale chrome=3 --scale firefox=3 --scale edge=1
```

Luego, al usar las herramientas del servidor, especifica el `grid_url`:

```python
start_browser(browser_type="chrome", grid_url="http://localhost:4444")
```

## Herramientas Disponibles

### 1. Gestión del Navegador

#### `start_browser`
Inicia un navegador web (Chrome o Firefox).

**Parámetros:**
- `browser_type` (string): Tipo de navegador - "chrome" o "firefox" (por defecto: "chrome")
- `grid_url` (string, opcional): URL de Selenium Grid (ej: "http://localhost:4444")

**Ejemplo:**
```python
# Navegador local
start_browser(browser_type="chrome")

# Con Selenium Grid
start_browser(browser_type="firefox", grid_url="http://localhost:4444")
```

**Retorna:** Confirmación de inicio exitoso del navegador

---

#### `close_browser`
Cierra el navegador actual.

**Ejemplo:**
```python
close_browser()
```

**Retorna:** Confirmación de cierre del navegador

---

### 2. Navegación

#### `navigate_to`
Navega a una URL específica.

**Parámetros:**
- `url` (string): La URL a visitar

**Ejemplo:**
```python
navigate_to(url="https://www.example.com")
```

**Retorna:** Confirmación con la URL actual

---

#### `get_page_title`
Obtiene el título de la página actual.

**Ejemplo:**
```python
get_page_title()
```

**Retorna:** El título de la página

---

### 3. Búsqueda y Manipulación de Elementos

#### `find_element`
Busca un elemento en la página.

**Parámetros:**
- `selector` (string): El selector para encontrar el elemento
- `by` (string): Tipo de selector - "id", "css", "xpath", "name", "class" (por defecto: "css")

**Ejemplo:**
```python
find_element(selector="#search-box", by="css")
find_element(selector="//input[@name='q']", by="xpath")
```

**Retorna:** Información del elemento encontrado (tag, visibilidad, estado, texto)

---

#### `click_element`
Hace clic en un elemento.

**Parámetros:**
- `selector` (string): El selector para encontrar el elemento
- `by` (string): Tipo de selector - "id", "css", "xpath", "name", "class" (por defecto: "css")

**Ejemplo:**
```python
click_element(selector="#submit-button", by="id")
click_element(selector="//button[@type='submit']", by="xpath")
```

**Retorna:** Confirmación de clic exitoso

---

#### `input_text`
Escribe texto en un campo de entrada.

**Parámetros:**
- `selector` (string): El selector para encontrar el elemento
- `text` (string): El texto a ingresar
- `by` (string): Tipo de selector - "id", "css", "xpath", "name", "class" (por defecto: "css")

**Ejemplo:**
```python
input_text(selector="#username", text="testuser", by="id")
input_text(selector="//input[@name='password']", text="secret123", by="xpath")
```

**Retorna:** Confirmación de texto ingresado

---

### 4. Verificación

#### `element_exists`
Verifica si un elemento existe en la página.

**Parámetros:**
- `selector` (string): El selector para encontrar el elemento
- `by` (string): Tipo de selector - "id", "css", "xpath", "name", "class" (por defecto: "css")

**Ejemplo:**
```python
element_exists(selector=".error-message", by="css")
```

**Retorna:** Boolean indicando si el elemento existe y cantidad de elementos encontrados

---

#### `get_element_text`
Obtiene el texto de un elemento.

**Parámetros:**
- `selector` (string): El selector para encontrar el elemento
- `by` (string): Tipo de selector - "id", "css", "xpath", "name", "class" (por defecto: "css")

**Ejemplo:**
```python
get_element_text(selector="#message", by="id")
get_element_text(selector="//div[@class='title']", by="xpath")
```

**Retorna:** El contenido de texto del elemento

---

#### `take_screenshot`
Captura una screenshot de la página actual.

**Parámetros:**
- `filename` (string, opcional): Nombre del archivo (se genera automáticamente si no se proporciona)

**Ejemplo:**
```python
take_screenshot()
take_screenshot(filename="login_page.png")
```

**Retorna:** Ruta del archivo de screenshot guardado

---

## Ejemplo de Uso Completo

```python
# 1. Iniciar navegador
start_browser(browser_type="chrome")

# 2. Navegar a un sitio web
navigate_to(url="https://www.example.com")

# 3. Verificar título de la página
get_page_title()

# 4. Buscar elemento
find_element(selector="#search-box", by="id")

# 5. Ingresar texto
input_text(selector="#search-box", text="selenium automation", by="id")

# 6. Hacer clic en botón de búsqueda
click_element(selector="#search-button", by="id")

# 7. Verificar que existen resultados
element_exists(selector=".search-results", by="css")

# 8. Obtener texto de un resultado
get_element_text(selector=".result-title", by="css")

# 9. Capturar screenshot
take_screenshot(filename="search_results.png")

# 10. Cerrar navegador
close_browser()
```

## Integración con el Proyecto MAT

Este MCP Selenium Server se integra con el proyecto MAT como parte del **MAT Context** (Recursos y Herramientas Externas). Según la arquitectura definida en `doc.md`:

- **AI Client**: Envía comandos al MCP Server
- **MCP Server**: Procesa los comandos y orquesta las acciones con el Selenium Server
- **Selenium MCP Server**: Ejecuta las acciones de automatización web

### Flujo de Integración

1. El usuario interactúa con el AI Client (ej: "crear pruebas automáticas")
2. El AI Client envía la solicitud al MCP Server MAT
3. El MCP Server MAT utiliza este Selenium Server para ejecutar acciones de navegador
4. Los resultados se devuelven al usuario a través del AI Client

## Selenium Grid - Configuración

Para usar Selenium Grid con este servidor, utiliza el archivo `docker-compose-v3.yml` del proyecto principal:

```bash
# Iniciar Selenium Grid con múltiples nodos
docker-compose -f docker-compose-v3.yml up -d --scale chrome=3 --scale firefox=3 --scale edge=1

# Verificar que el Grid está funcionando
curl http://localhost:4444/status

# Detener el Grid
docker-compose -f docker-compose-v3.yml down
```

El Grid estará disponible en `http://localhost:4444` y podrás usar esta URL al iniciar el navegador con `start_browser()`.

## Estructura de Archivos

```
mcp-selenium-server/
├── server.py           # Servidor MCP principal con todas las herramientas
├── requirements.txt    # Dependencias del proyecto
├── README.md          # Esta documentación
└── screenshots/       # Directorio para capturas de pantalla (se crea automáticamente)
```

## Mejores Prácticas MCP

Este servidor sigue las mejores prácticas del Model Context Protocol:

1. ✅ **Descripción clara de herramientas**: Cada tool tiene documentación completa
2. ✅ **Tipado de parámetros**: Todos los parámetros están correctamente tipados
3. ✅ **Manejo de errores**: Captura y retorna errores de forma descriptiva
4. ✅ **Estado gestionado**: Mantiene el estado del navegador de forma segura
5. ✅ **Respuestas informativas**: Todas las operaciones retornan mensajes claros

## Solución de Problemas

### Error: "Browser is not started"
**Solución:** Ejecuta `start_browser()` antes de cualquier otra operación.

### Error: "Element not found (timeout)"
**Solución:** Verifica que el selector sea correcto y que el elemento exista en la página. Los timeouts por defecto son de 10 segundos.

### Error al instalar dependencias
**Solución:** Asegúrate de tener Python 3.8+ y pip actualizado:
```bash
python3 --version
pip install --upgrade pip
```

### Problemas con ChromeDriver/GeckoDriver
**Solución:** El paquete `webdriver-manager` se encarga de descargar e instalar los drivers automáticamente. Si hay problemas, verifica tu conexión a internet.

## Contribuciones

Este proyecto forma parte del repositorio [juananmora/seleniumchatgpt4](https://github.com/juananmora/seleniumchatgpt4). Las contribuciones son bienvenidas siguiendo las guías del proyecto principal.

## Licencia

Este proyecto está bajo la licencia Apache License 2.0, al igual que el proyecto principal.

## Referencias

- [Documentación del proyecto MAT](../doc.md)
- [README principal del proyecto](../README.md)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [FastMCP Documentation](https://github.com/jlowin/fastmcp)
