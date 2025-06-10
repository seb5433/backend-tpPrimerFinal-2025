# Análisis Técnico del Proyecto

## **1. ORM utilizado y mapeo**

Se utiliza **JPA (Java Persistence API)** como ORM. El mapeo se realiza mediante **anotaciones**:

- **Entidades**: Se mapean con `@Entity` y `@Table`
- **Campos**: Con `@Column`, `@Id`, `@GeneratedValue`
- **Relaciones**: Con `@ManyToOne`, `@OneToMany`, `@ManyToMany`, `@JoinColumn`, `@JoinTable`

Ejemplo en `Cliente`:
```java
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
}
```

**Consulta de objetos**: Se utiliza `EntityManager` con JPQL:
```java
Query q = em.createQuery("SELECT c FROM Cliente c ORDER BY c.apellido, c.nombre");
```

## **2. Tecnología del Frontend**

- **Framework**: **React** (evidenciado en `App.jsx` y componentes `.jsx`)
- **Gestor de paquetes**: **NPM** (se observa `package.json`)
- **Bundler**: **Vite** (configurado en `vite.config.js`)

## **3. Estructura del Backend**

```
backend/src/main/java/py/com/progweb/examenfinal/
├── dto/           # Data Transfer Objects
├── ejb/           # Enterprise Java Beans (interfaces DAO)
├── ejb/impl/      # Implementaciones de los DAOs
├── input/         # Clases para recibir datos de entrada
├── model/         # Entidades JPA
└── rest/          # Controladores REST
```

## **4. Manejador de Dependencias**

Se utiliza **Apache Maven**:
- Archivo de configuración: `pom.xml`
- Comando de construcción: `mvn clean install` (visible en `deploy.sh`)

## **5. Servidor de Aplicaciones**

**WildFly 18.0.1.Final** (directorio `wildfly-18.0.1.Final/`)

**Configuraciones**:
- Despliegue automático copiando el WAR al directorio de despliegue
- Scripts de despliegue: `deploy.sh` y `deploy.bat`
- Configuración de persistencia con `unitName = "EcommerceBD"`

## **6. Arquitectura de Capas del Backend**

### **Capa de Presentación (REST)**
- **Ubicación**: `rest/`
- **Función**: Controladores REST que exponen endpoints
- **Ejemplos**: `ClienteREST`, `ServicioREST`

### **Capa de Lógica de Negocio (EJB)**
- **Interfaces**: `ejb/` - Definen contratos
- **Implementaciones**: `ejb/impl/` - Lógica de negocio
- **Patrón**: DAO (Data Access Object)

### **Capa de Persistencia (JPA)**
- **Entidades**: `model/`
- **ORM**: JPA con EntityManager
- **Base de datos**: PostgreSQL (puerto 5433)

### **Capa de Transferencia de Datos**
- **DTOs**: `dto/` - Para respuestas
- **Input Objects**: `input/` - Para entrada de datos

### **Flujo de Datos**:
```
REST Controller → EJB DAO → JPA Entity → Database
     ↓              ↓          ↓
   Input DTO  →  Business  →  Model  →  PostgreSQL
```

La arquitectura sigue el patrón **MVC** con separación clara de responsabilidades y utiliza **EJB** para la gestión de transacciones y lógica de negocio.