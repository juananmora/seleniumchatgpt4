#!/usr/bin/env python3
"""
MCP Selenium Server - Model Context Protocol Server for Selenium automation
Provides standardized tools for web browser automation using Selenium WebDriver
"""

from mcp.server.fastmcp import FastMCP
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.firefox.service import Service as FirefoxService
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
import os
from datetime import datetime

# Initialize FastMCP server
mcp = FastMCP("Selenium MCP Server")

# Global variable to store the WebDriver instance
driver = None

def get_by_type(by: str) -> By:
    """Convert string selector type to Selenium By type"""
    by_mapping = {
        "id": By.ID,
        "css": By.CSS_SELECTOR,
        "xpath": By.XPATH,
        "name": By.NAME,
        "class": By.CLASS_NAME,
        "tag": By.TAG_NAME,
        "link_text": By.LINK_TEXT,
        "partial_link_text": By.PARTIAL_LINK_TEXT
    }
    return by_mapping.get(by.lower(), By.CSS_SELECTOR)

@mcp.tool()
def start_browser(browser_type: str = "chrome", grid_url: str = None) -> str:
    """
    Initialize a web browser (Chrome or Firefox)
    
    Args:
        browser_type: Type of browser to start ("chrome" or "firefox")
        grid_url: Optional URL for Selenium Grid connection (e.g., "http://localhost:4444")
    
    Returns:
        Confirmation message with browser type started
    """
    global driver
    
    if driver is not None:
        return "Browser is already running. Please close it first before starting a new one."
    
    try:
        browser_type = browser_type.lower()
        
        if grid_url:
            # Connect to Selenium Grid
            if browser_type == "chrome":
                options = webdriver.ChromeOptions()
                driver = webdriver.Remote(
                    command_executor=grid_url,
                    options=options
                )
            elif browser_type == "firefox":
                options = webdriver.FirefoxOptions()
                driver = webdriver.Remote(
                    command_executor=grid_url,
                    options=options
                )
            else:
                return f"Unsupported browser type: {browser_type}. Use 'chrome' or 'firefox'."
            return f"{browser_type.capitalize()} browser started successfully using Selenium Grid at {grid_url}"
        else:
            # Start local browser
            if browser_type == "chrome":
                service = ChromeService(ChromeDriverManager().install())
                driver = webdriver.Chrome(service=service)
            elif browser_type == "firefox":
                service = FirefoxService(GeckoDriverManager().install())
                driver = webdriver.Firefox(service=service)
            else:
                return f"Unsupported browser type: {browser_type}. Use 'chrome' or 'firefox'."
            
            return f"{browser_type.capitalize()} browser started successfully"
    except Exception as e:
        driver = None
        return f"Error starting browser: {str(e)}"

@mcp.tool()
def close_browser() -> str:
    """
    Close the current browser session
    
    Returns:
        Confirmation message of browser closure
    """
    global driver
    
    if driver is None:
        return "No browser is currently running"
    
    try:
        driver.quit()
        driver = None
        return "Browser closed successfully"
    except Exception as e:
        driver = None
        return f"Error closing browser: {str(e)}"

@mcp.tool()
def navigate_to(url: str) -> str:
    """
    Navigate to a specific URL
    
    Args:
        url: The URL to navigate to
    
    Returns:
        Confirmation message with the current URL
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        driver.get(url)
        return f"Successfully navigated to: {driver.current_url}"
    except Exception as e:
        return f"Error navigating to URL: {str(e)}"

@mcp.tool()
def find_element(selector: str, by: str = "css") -> str:
    """
    Find an element on the page by selector
    
    Args:
        selector: The selector string to find the element
        by: The type of selector ("id", "css", "xpath", "name", "class")
    
    Returns:
        Information about the found element or error message
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        by_type = get_by_type(by)
        wait = WebDriverWait(driver, 10)
        element = wait.until(EC.presence_of_element_located((by_type, selector)))
        
        tag_name = element.tag_name
        element_text = element.text[:100] if element.text else ""
        is_displayed = element.is_displayed()
        is_enabled = element.is_enabled()
        
        return f"Element found - Tag: {tag_name}, Displayed: {is_displayed}, Enabled: {is_enabled}, Text: '{element_text}'"
    except TimeoutException:
        return f"Element not found with {by}='{selector}' (timeout)"
    except Exception as e:
        return f"Error finding element: {str(e)}"

@mcp.tool()
def click_element(selector: str, by: str = "css") -> str:
    """
    Click on an element
    
    Args:
        selector: The selector string to find the element
        by: The type of selector ("id", "css", "xpath", "name", "class")
    
    Returns:
        Confirmation message of successful click
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        by_type = get_by_type(by)
        wait = WebDriverWait(driver, 10)
        element = wait.until(EC.element_to_be_clickable((by_type, selector)))
        element.click()
        return f"Successfully clicked element with {by}='{selector}'"
    except TimeoutException:
        return f"Element not clickable with {by}='{selector}' (timeout)"
    except Exception as e:
        return f"Error clicking element: {str(e)}"

@mcp.tool()
def input_text(selector: str, text: str, by: str = "css") -> str:
    """
    Input text into a form field
    
    Args:
        selector: The selector string to find the input element
        text: The text to input
        by: The type of selector ("id", "css", "xpath", "name", "class")
    
    Returns:
        Confirmation message of text input
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        by_type = get_by_type(by)
        wait = WebDriverWait(driver, 10)
        element = wait.until(EC.presence_of_element_located((by_type, selector)))
        element.clear()
        element.send_keys(text)
        return f"Successfully input text into element with {by}='{selector}'"
    except TimeoutException:
        return f"Element not found with {by}='{selector}' (timeout)"
    except Exception as e:
        return f"Error inputting text: {str(e)}"

@mcp.tool()
def get_page_title() -> str:
    """
    Get the title of the current page
    
    Returns:
        The page title or error message
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        title = driver.title
        return f"Page title: {title}"
    except Exception as e:
        return f"Error getting page title: {str(e)}"

@mcp.tool()
def element_exists(selector: str, by: str = "css") -> str:
    """
    Check if an element exists on the page
    
    Args:
        selector: The selector string to find the element
        by: The type of selector ("id", "css", "xpath", "name", "class")
    
    Returns:
        Boolean result indicating if element exists
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        by_type = get_by_type(by)
        elements = driver.find_elements(by_type, selector)
        exists = len(elements) > 0
        return f"Element exists: {exists} (found {len(elements)} element(s))"
    except Exception as e:
        return f"Error checking element existence: {str(e)}"

@mcp.tool()
def get_element_text(selector: str, by: str = "css") -> str:
    """
    Get the text content of an element
    
    Args:
        selector: The selector string to find the element
        by: The type of selector ("id", "css", "xpath", "name", "class")
    
    Returns:
        The text content of the element
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        by_type = get_by_type(by)
        wait = WebDriverWait(driver, 10)
        element = wait.until(EC.presence_of_element_located((by_type, selector)))
        text = element.text
        return f"Element text: {text}"
    except TimeoutException:
        return f"Element not found with {by}='{selector}' (timeout)"
    except Exception as e:
        return f"Error getting element text: {str(e)}"

@mcp.tool()
def take_screenshot(filename: str = None) -> str:
    """
    Take a screenshot of the current page
    
    Args:
        filename: Optional filename for the screenshot (defaults to timestamp-based name)
    
    Returns:
        Path to the saved screenshot file
    """
    global driver
    
    if driver is None:
        return "Browser is not started. Please start the browser first using start_browser()."
    
    try:
        # Create screenshots directory if it doesn't exist
        screenshots_dir = os.path.join(os.path.dirname(__file__), "screenshots")
        os.makedirs(screenshots_dir, exist_ok=True)
        
        # Generate filename if not provided
        if filename is None:
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            filename = f"screenshot_{timestamp}.png"
        
        # Ensure .png extension
        if not filename.endswith('.png'):
            filename += '.png'
        
        filepath = os.path.join(screenshots_dir, filename)
        driver.save_screenshot(filepath)
        
        return f"Screenshot saved to: {filepath}"
    except Exception as e:
        return f"Error taking screenshot: {str(e)}"

if __name__ == "__main__":
    # Run the MCP server
    mcp.run()
