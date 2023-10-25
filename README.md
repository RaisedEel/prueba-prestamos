# prueba-prestamos

Este pequeño proyecto (hecho por medio de Spring Boot) fue construido durante un plazo de 3 horas para una prueba
técnica para una posición Backend.
Posteriormente, algunas partes de la lógica han sido limpiadas para facilitar el seguimiento del procedimiento usado en
el código.

## Objetivo

El objetivo de esta aplicación es calcular los pagos para finiquitar préstamos pendientes ya existentes en la base de
datos.

## Dependencias

El proyecto utiliza Maven para gestionar las dependencias:

- Spring Web: Contiene un servidor web Tomcat y también permite conversión entre JSON y objetos Java.
- Spring JPA: Permite comunicación con una base de datos al mapear objetos Java con registros en la base de datos.
- H2 Database: Una base de datos local que es usada por JPA para almacenar información. Se reinicia al finalizar
  la ejecución de la aplicación.
- Lombok: Permite crear constructores, setters y getters automáticamente durante compilación.

## Funcionamiento

La aplicación solo permite la obtención de pagos para los préstamos pendientes, los prestamos y tasas de intereses se
ingresan a la base de datos automáticamente durante su inicialización.

Las funciones usadas para el cálculo son las siguientes:

```markdown
Interés = monto prestado * porcentaje del interés * (días transcurridos / año comercial)
IVA = monto prestado * tasa iva
Pago = monto prestado + Interés + IVA

Donde:

- monto prestado → monto original del préstamo pedido.
- porcentaje del interés → porcentaje convertido a decimales (ejemplo: 5% = 0.005).
- días transcurridos → días transcurridos desde la adquisición del préstamo.
- año comercial → número de días en el año comercial (usualmente 360 días).
- tasa iva → el porcentaje del iva convertido a decimales (ejemplo: 16% = 0.016).
```

Todos estos valores son obtenidos de la base de datos o por medio de peticiones del cliente.

Las tasa de interés usada en el cálculo depende del plazo de días. Todas las tasas se muestran a continuación:

| Plazo mínimo | Plazo máximo | Porcentaje |
|--------------|--------------|------------|
| 1            | 1            | 7.00%      |
| 2            | 7            | 6.50%      |
| 8            | 15           | 6.00%      |
| 16           | 30           | 5.50%      |
| 31           | 360          | 5.00%      |

## Obtener pago

Como se mencionó la aplicación solo puede calcular pagos, para acceder a esta operación se hace una petición POST al
endpoint `localhost:8080/pagos` ya que el servidor es local.

La petición debe llevar los siguiente campos:

- fechaActual: Una cadena con una fecha en el formato dd-MM-yyyy. Ejemplos: "01-01-2023", "31-12-2023", "29-02-2020".
- cliente: Una cadena conteniendo el número/id del cliente.
- iva: Una cadena conteniendo el porcentaje del iva, por ejemplo: "16%".
- anoComercial: El número de días en un año comercial.

En el JSON de la respuesta se pondrán encontrar el monto original del préstamo, el interés e iva calculados y
finalmente el pago.

```markdown
Para probar el funcionamiento de la aplicación se puede usar el cliente "00103228" que cuenta con 4 préstamos
ya ingresados.
```