# Guía de Migración a MongoDB

## Resumen de Cambios

Este proyecto ha sido migrado de una base de datos SQL (Oracle) a MongoDB. A continuación se detallan todos los cambios realizados y cómo usar el nuevo sistema.

## Cambios Realizados

### 1. Dependencias (pom.xml)
- ✅ Reemplazado `spring-boot-starter-data-jpa` por `spring-boot-starter-data-mongodb`
- ✅ Eliminado driver de Oracle (`ojdbc8`)
- ✅ Eliminadas dependencias de Hibernate

### 2. Configuración (application.properties)
```properties
# Antes (SQL/Oracle)
spring.datasource.url=jdbc:oracle:thin:@...
spring.jpa.hibernate.ddl-auto=none
...

# Ahora (MongoDB)
spring.data.mongodb.uri=mongodb://localhost:27017/proyecto_db
logging.level.org.springframework.data.mongodb.core=DEBUG
```

**IMPORTANTE**: Actualiza la URI de MongoDB según tu configuración:
- Para MongoDB local: `mongodb://localhost:27017/proyecto_db`
- Para MongoDB Atlas: `mongodb+srv://usuario:password@cluster.mongodb.net/proyecto_db`

### 3. Modelo de Datos

#### Colecciones Principales

##### 📄 USUARIOS
```java
Usuario {
    id: String (ObjectId generado por MongoDB)
    nombre: String
    numeroCelular: String
    numeroCedula: String
    correoElectronico: String
    tipoUsuario: String

    // Subdocumentos embebidos
    servicios: List<Servicio>
    viajes: List<Viaje>
    tarjetasCredito: List<TarjetaCredito>
    reviews: List<Review>
}
```

**Subdocumentos de Usuario**:
- `Servicio`: tipoServicio, nivelRequerido, estado, orden, restaurante, puntoOrigen, puntoDestino, fechaHora
- `Viaje`: idConductor, nivelRequerido, estado, orden, restaurante, fechas, longitud, costo
- `TarjetaCredito`: titular, numero, fechaExpiracion, codigoSeguridad
- `Review`: puntuacion, comentario, fecha, idUsuarioPublicado

##### 🚗 VEHICULOS
```java
Vehiculo {
    id: String
    idConductor: String (referencia a Usuario._id)
    nivelVehiculo: String
    tipo: String
    marca: String
    modelo: String
    color: String
    placa: String
    ciudadExpedicion: String
    capacidadPasajeros: Integer

    // Subdocumentos
    disponibilidad: Disponibilidad
    franjasHorarias: List<FranjaHoraria>
}
```

**Subdocumentos de Vehiculo**:
- `Disponibilidad`: disponible (String: "Y"/"N")
- `FranjaHoraria`: diaSemana, horaInicio, horaFin, tipoServicio

##### 📍 PUNTOS_GEOGRAFICOS
```java
PuntoGeografico {
    id: String
    nombre: String
    latitud: Double
    longitud: Double
    direccionAproximada: String
    ciudad: String
}
```

##### 💰 TARIFAS
```java
Tarifa {
    id: String
    tipoServicio: String
    nivel: String
    precioPorKM: Double
}
```

## Nuevos Archivos Creados

### Documentos (Entities)
- ✅ `Usuario.java` - Documento principal con subdocumentos embebidos
- ✅ `Vehiculo.java` - Documento con disponibilidad y franjas horarias
- ✅ `PuntoGeografico.java` - Documento independiente
- ✅ `Tarifa.java` - Documento independiente

### Repositorios
- ✅ `MongoUsuarioRepository.java`
- ✅ `MongoVehiculoRepository.java`
- ✅ `MongoPuntoGeograficoRepository.java`
- ✅ `MongoTarifaRepository.java`

### Servicios
- ✅ `MongoUsuarioService.java`
- ✅ `MongoVehiculoService.java`
- ✅ `MongoPuntoGeograficoService.java`
- ✅ `MongoTarifaService.java`

### Controladores
- ✅ `MongoUsuarioController.java` (ejemplo completo)

## Cómo Usar el Sistema

### 1. Configurar MongoDB

**Opción A: MongoDB Local**
```bash
# Instalar MongoDB
# Iniciar servicio
mongod --dbpath /path/to/data

# La aplicación se conectará automáticamente a localhost:27017
```

**Opción B: MongoDB Atlas (Cloud)**
1. Crea una cuenta en https://www.mongodb.com/cloud/atlas
2. Crea un cluster gratuito
3. Obtén la cadena de conexión
4. Actualiza `application.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://tu-usuario:tu-password@cluster.mongodb.net/proyecto_db
```

### 2. Ejecutar la Aplicación

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar
mvn spring-boot:run
```

### 3. Probar los Endpoints

#### Crear Usuario Simple
```bash
POST http://localhost:8080/mongo/usuarios
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "numeroCelular": "3001234567",
  "numeroCedula": "1234567890",
  "correoElectronico": "juan@example.com",
  "tipoUsuario": "Cliente"
}
```

#### Crear Cliente con Tarjeta (RF2)
```bash
POST http://localhost:8080/mongo/usuarios/new/cliente
Content-Type: application/json

{
  "usuario": {
    "nombre": "María García",
    "numeroCelular": "3009876543",
    "numeroCedula": "0987654321",
    "correoElectronico": "maria@example.com"
  },
  "tarjeta": {
    "titularDeLaTarjeta": "María García",
    "numeroTarjeta": "4111111111111111",
    "fechaExpiracion": "2025-12-31",
    "codigoSeguridad": 123
  }
}
```

#### Registrar Conductor (RF3)
```bash
POST http://localhost:8080/mongo/usuarios/new/conductor
Content-Type: application/json

{
  "nombre": "Carlos López",
  "numeroCelular": "3001112233",
  "numeroCedula": "1122334455",
  "correoElectronico": "carlos@example.com"
}
```

#### Agregar Servicio a Usuario
```bash
POST http://localhost:8080/mongo/usuarios/{id}/servicios
Content-Type: application/json

{
  "tipoServicio": "Transporte",
  "nivelRequerido": "Premium",
  "estado": "Pendiente",
  "puntoOrigen": "Calle 123",
  "fechaHora": "2025-11-29T10:00:00"
}
```

#### Crear Vehículo
```bash
POST http://localhost:8080/mongo/vehiculos
Content-Type: application/json

{
  "idConductor": "673faa12b45c6789abcd1234",
  "nivelVehiculo": "Premium",
  "tipo": "Sedan",
  "marca": "Toyota",
  "modelo": "Corolla",
  "color": "Negro",
  "placa": "ABC123",
  "ciudadExpedicion": "Bogotá",
  "capacidadPasajeros": 4,
  "disponibilidad": {
    "disponible": "Y"
  }
}
```

## Diferencias Clave entre SQL y MongoDB

### Antes (JPA/SQL)
```java
// Relaciones con tablas separadas
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUsuario;

    @OneToMany(mappedBy = "cliente")
    private List<TarjetaCreditoEntity> tarjetas;
}

// Guardar tarjeta requiere dos operaciones
usuario = usuarioRepository.save(usuario);
tarjeta.setCliente(usuario);
tarjetaRepository.save(tarjeta);
```

### Ahora (MongoDB)
```java
// Subdocumentos embebidos
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    // Subdocumento embebido directamente
    private List<TarjetaCredito> tarjetasCredito;

    public static class TarjetaCredito {
        private String numeroTarjeta;
        // ...
    }
}

// Guardar todo en una sola operación
usuario.getTarjetasCredito().add(tarjeta);
usuarioRepository.save(usuario);
```

### Ventajas de la Estructura MongoDB

1. **Menos Consultas**: Los datos relacionados están en un solo documento
2. **Transacciones Simplificadas**: Una operación atómica para el documento completo
3. **Mejor Performance**: No hay JOINs, todo está en un documento
4. **Escalabilidad**: MongoDB escala horizontalmente con facilidad
5. **Flexibilidad**: Esquema dinámico permite evolución del modelo

## Archivos Antiguos

Los archivos antiguos de JPA aún existen en el proyecto pero no se usan:
- `entities/*Entity.java` - Entidades JPA (antiguas)
- `repositories/*Repository.java` (excepto Mongo*) - Repositorios JPA (antiguos)
- Algunos servicios y controladores antiguos

**Puedes eliminarlos si ya no los necesitas**, o mantenerlos por si necesitas consultarlos.

## Próximos Pasos

1. ✅ Configurar MongoDB (local o Atlas)
2. ✅ Actualizar `application.properties` con tu URI de MongoDB
3. ✅ Probar los endpoints con Postman o curl
4. 🔄 Migrar datos existentes de Oracle a MongoDB (si aplica)
5. 🔄 Adaptar los controladores restantes para usar los nuevos servicios Mongo
6. 🔄 Actualizar tests unitarios e integración

## Migración de Datos

Si tienes datos en Oracle que quieres migrar a MongoDB:

```java
// Script de ejemplo para migrar usuarios
List<UsuarioEntity> usuariosSQL = usuarioRepository.findAll();
for (UsuarioEntity usuarioSQL : usuariosSQL) {
    Usuario usuarioMongo = new Usuario(
        usuarioSQL.getNombre(),
        usuarioSQL.getNumeroCelular(),
        usuarioSQL.getNumeroCedula(),
        usuarioSQL.getCorreoElectronico(),
        usuarioSQL.getTipo()
    );
    mongoUsuarioRepository.save(usuarioMongo);
}
```

## Soporte

Si tienes problemas:
1. Verifica que MongoDB esté corriendo: `mongosh` o MongoDB Compass
2. Revisa los logs de la aplicación
3. Confirma que la URI en application.properties es correcta
4. Verifica que las colecciones se están creando: `show collections` en mongosh

## Recursos Adicionales

- [Spring Data MongoDB Docs](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [MongoDB Manual](https://docs.mongodb.com/manual/)
- [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
