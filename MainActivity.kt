package com.kevin.ruta2027

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Green = Color(0xFF45C13A)
private val GreenDark = Color(0xFF277A21)
private val Ink = Color(0xFF151915)
private val Muted = Color(0xFF687168)
private val Page = Color(0xFFF4F6F2)
private val Line = Color(0xFFE5E9E2)
private val White = Color(0xFFFFFFFF)
private val SoftGreen = Color(0xFFEAF8E7)
private val Spanish = Locale("es", "CO")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(244, 246, 242)
        window.navigationBarColor = android.graphics.Color.WHITE
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Green,
                    onPrimary = White,
                    secondary = GreenDark,
                    background = Page,
                    surface = White,
                    onSurface = Ink,
                    onBackground = Ink
                )
            ) {
                Surface(color = Page, modifier = Modifier.fillMaxSize()) {
                    RutaApp(getSharedPreferences("ruta_local", MODE_PRIVATE))
                }
            }
        }
    }
}

private data class Task(val id: String, val title: String, val detail: String = "")

private data class WeekPlan(
    val number: Int,
    val title: String,
    val dateLabel: String,
    val start: LocalDate,
    val end: LocalDate,
    val phase: String,
    val tasks: List<String>,
    val deliverable: String,
    val notionUrl: String = ""
)

private data class Exercise(val id: String, val name: String, val sets: String)
private data class Workout(val title: String, val focus: String, val exercises: List<Exercise>)
private data class CourseInfo(val title: String, val video: String, val real: String, val result: String)
private data class InterviewTopic(
    val id: String,
    val title: String,
    val short: String,
    val why: String,
    val example: String,
    val extended: String,
    val followUp: String,
    val followUpAnswer: String
)

private val roadmap = listOf(
    WeekPlan(0, "Reactivación", "25–30 sep 2026", LocalDate.of(2026, 9, 25), LocalDate.of(2026, 9, 30), "Arranque", listOf(
        "Verificar JDK, IntelliJ, PostgreSQL, MySQL, Docker y Git.",
        "Crear el repositorio maestro, README y reglas de commits.",
        "Hacer diagnóstico sin IA: variables, clases, SELECT, INSERT, JOIN y Git.",
        "Iniciar SQL con un esquema pequeño de usuarios, libros y préstamos."
    ), "Entorno comprobado, repositorio creado y diagnóstico registrado.", "https://app.notion.com/p/Semana-0-Reactivaci-n-26575c5bd3e74cfe9250bfe28e783667?pvs=21"),
    WeekPlan(1, "SQL fundamental", "1–7 oct 2026", LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 7), "Cimientos", listOf(
        "Practicar SELECT, filtros, ordenamiento y agregaciones.",
        "Dominar JOINs y el modelo relacional.",
        "Escribir 25 consultas a mano, sin copiar.",
        "Empezar el proyecto Bookify y explicar cada consulta."
    ), "25 consultas propias y una base de datos pequeña que puedas explicar.", "https://app.notion.com/p/Semana-1-SQL-fundamental-495a80d27a5944a7a01ab54b52cfa70f?pvs=21"),
    WeekPlan(2, "Git + proyecto SQL", "8–14 oct 2026", LocalDate.of(2026, 10, 8), LocalDate.of(2026, 10, 14), "Cimientos", listOf(
        "Repasar Git + GitHub a 1,5× solo si el tema ya se entiende.",
        "Practicar ramas, commits limpios y pull requests.",
        "Resolver un conflicto y registrar el proceso.",
        "Continuar Bookify y dejar un README profesional."
    ), "Repositorio maestro con ramas, pull request, conflicto resuelto y proyecto SQL.", "https://app.notion.com/p/Semana-2-Git-proyecto-SQL-d7c6cca2d642452fba2a9cec6b9ae644?pvs=21"),
    WeekPlan(3, "Java: fundamentos, POO y cierre del Máster", "15–21 oct 2026", LocalDate.of(2026, 10, 15), LocalDate.of(2026, 10, 21), "Cimientos", listOf(
        "Terminar el 1 % restante del Máster Completo Java.",
        "Cerrar vacíos detectados; no volver a ver el curso completo.",
        "Resolver POO y ejercicios pequeños sin asistente.",
        "Validar Java con Bookify y explicar el código sin leer."
    ), "Bookify funcional y prueba práctica de Java aprobada.", "https://app.notion.com/p/Semana-3-Java-fundamentos-POO-y-cierre-del-M-ster-37a1902f1bd44454b760751c1009371f?pvs=21"),
    WeekPlan(4, "Java: colecciones, JSON y MVC", "22–31 oct 2026", LocalDate.of(2026, 10, 22), LocalDate.of(2026, 10, 31), "Cimientos", listOf(
        "Repasar Collections, JSON, MVC, Lombok y buenas prácticas.",
        "Practicar con el curso de Java solo en los temas débiles.",
        "Crear una aplicación de consola y persistencia simple."
    ), "Aplicación de consola ejecutable y explicable.", "https://app.notion.com/p/Semana-4-Java-colecciones-JSON-y-MVC-c4eabe003ca54abc9cab2403913e6dec?pvs=21"),
    WeekPlan(5, "Java avanzado: JDBC y Streams", "1–7 nov 2026", LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 7), "Backend", listOf(
        "Practicar generics, Streams, Optional y fechas.",
        "Conectar Java a SQL con JDBC.",
        "Entender pools de conexiones y cuándo tienen sentido."
    ), "Ejercicio Java con consultas JDBC y procesamiento de colecciones.", "https://app.notion.com/p/Semana-5-Java-avanzado-JDBC-y-Streams-fa8f124bce3c4f39a8f83f2f87e83600?pvs=21"),
    WeekPlan(6, "Java avanzado: concurrencia y RxJava", "8–14 nov 2026", LocalDate.of(2026, 11, 8), LocalDate.of(2026, 11, 14), "Backend", listOf(
        "Practicar hilos, Executors y conceptos de concurrencia.",
        "Revisar RxJava y patrones solo después de entender la base.",
        "Añadir pruebas al programa concurrente."
    ), "Programa concurrente pequeño con pruebas y explicación de decisiones.", "https://app.notion.com/p/Semana-6-Java-avanzado-concurrencia-y-RxJava-95f24c0c1a3d4697b493eeea0874dc82?pvs=21"),
    WeekPlan(7, "Spring Boot: API y persistencia", "15–21 nov 2026", LocalDate.of(2026, 11, 15), LocalDate.of(2026, 11, 21), "Backend", listOf(
        "Continuar Spring Boot & Spring Framework desde el 17 %.",
        "Construir configuración, capas, entidades y relaciones JPA.",
        "Usar DTOs y separar responsabilidades."
    ), "API con persistencia y capas claras.", "https://app.notion.com/p/Semana-7-Spring-Boot-API-y-persistencia-3a58f48bd4944a488f3c0523b777f95a?pvs=21"),
    WeekPlan(8, "Spring Boot: JUnit, seguridad y testing", "22–30 nov 2026", LocalDate.of(2026, 11, 22), LocalDate.of(2026, 11, 30), "Backend", listOf(
        "Construir endpoints REST y validación.",
        "Aplicar JWT y Spring Security.",
        "Usar JUnit 5 y Mockito en servicios y controladores.",
        "Probar la API desde una carpeta limpia sin IA."
    ), "API monolítica funcional, protegida y probada.", "https://app.notion.com/p/Semana-8-Spring-Boot-JUnit-seguridad-y-testing-f33af40434fa4c70bcd3639f4b7479b5?pvs=21"),
    WeekPlan(9, "Docker, Compose y Kubernetes inicial", "1–7 dic 2026", LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 7), "Arquitectura", listOf(
        "Crear Dockerfile e imágenes.",
        "Configurar Compose, redes y volúmenes.",
        "Priorizar Docker y Compose; Kubernetes se queda introductorio."
    ), "API + PostgreSQL levantan con Docker Compose.", "https://app.notion.com/p/Semana-9-Docker-Compose-y-Kubernetes-inicial-183c9c41307c43a2bd450c5f12557b40?pvs=21"),
    WeekPlan(10, "Clean Architecture, Hexagonal y DDD", "8–14 dic 2026", LocalDate.of(2026, 12, 8), LocalDate.of(2026, 12, 14), "Arquitectura", listOf(
        "Identificar dependencias y límites entre capas.",
        "Refactorizar a arquitectura limpia/hexagonal con criterio.",
        "Aplicar DDD cuando aporte, no por moda.",
        "Registrar decisiones técnicas en ADRs."
    ), "Refactor demostrado con decisiones de arquitectura documentadas.", "https://app.notion.com/p/Semana-10-Clean-Architecture-Hexagonal-y-DDD-79a93cd004a14aae8b2767ab958d0213?pvs=21"),
    WeekPlan(11, "Eureka, microservicios y RabbitMQ", "15–21 dic 2026", LocalDate.of(2026, 12, 15), LocalDate.of(2026, 12, 21), "Arquitectura", listOf(
        "Definir límites de los servicios.",
        "Separar dos servicios del monolito solo cuando esté estable.",
        "Probar comunicación y conceptos de Eureka/RabbitMQ."
    ), "Dos servicios con límites claros y una demo pequeña.", "https://app.notion.com/p/Semana-11-Eureka-microservicios-y-RabbitMQ-6aa59691385340a7b3c351b8f98e7e42?pvs=21"),
    WeekPlan(12, "Gateway, resiliencia y observabilidad", "22–31 dic 2026", LocalDate.of(2026, 12, 22), LocalDate.of(2026, 12, 31), "Arquitectura", listOf(
        "Entender Gateway, resiliencia, seguridad y observabilidad.",
        "Repasar Kubernetes a nivel introductorio.",
        "Priorizar terminar la arquitectura si las fiestas reducen horas."
    ), "Demo desplegable, README y guía de ejecución.", "https://app.notion.com/p/Semana-12-Gateway-resiliencia-observabilidad-y-Kubernetes-b505b354a42f4e3f908208ea8e353b9b?pvs=21"),
    WeekPlan(13, "AWS básico", "1–7 ene 2027", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 7), "IA aplicada", listOf(
        "Entender IAM, redes, cómputo y almacenamiento.",
        "Revisar bases de datos y costos.",
        "Validar las pantallas del curso antiguo con la documentación vigente."
    ), "Despliegue sencillo y costos/riesgos explicados.", "https://app.notion.com/p/Semana-13-AWS-b-sico-cf647ee4bb41426587e5a672b497311f?pvs=21"),
    WeekPlan(14, "Spring AI: Gemini y tool calling", "8–14 ene 2027", LocalDate.of(2027, 1, 8), LocalDate.of(2027, 1, 14), "IA aplicada", listOf(
        "Conectar Spring AI con Gemini.",
        "Practicar prompts, tool calling y manejo de errores.",
        "Limitar la función a un caso útil dentro de la API."
    ), "Funcionalidad de IA integrada y explicable.", "https://app.notion.com/p/Semana-14-Spring-AI-Gemini-y-tool-calling-71db49c1053342fbb8af2df2a5999923?pvs=21"),
    WeekPlan(15, "Spring AI: RAG, agentes y MCP", "15–21 ene 2027", LocalDate.of(2027, 1, 15), LocalDate.of(2027, 1, 21), "IA aplicada", listOf(
        "Entender RAG, agentes y MCP.",
        "Conectar la capacidad a tu API.",
        "Probar errores, límites y resultados."
    ), "Una función útil conectada a tu backend, con pruebas manuales.", "https://app.notion.com/p/Semana-15-Spring-AI-RAG-agentes-y-MCP-6875be2dcc6b4221ad4bcb0266b0de6c?pvs=21"),
    WeekPlan(16, "Ingeniería de LLM y prototipo", "22–31 ene 2027", LocalDate.of(2027, 1, 22), LocalDate.of(2027, 1, 31), "IA aplicada", listOf(
        "Revisar inferencia, APIs y modelos abiertos.",
        "Explorar Hugging Face, Gradio y evaluación.",
        "Construir un prototipo acotado; no reemplazar fundamentos por más herramientas."
    ), "Primer prototipo de IA y decisión justificada de interfaz.", "https://app.notion.com/p/Semana-16-Ingenier-a-de-LLM-prototipo-y-decisi-n-de-interfaz-8200480e3cb2408884a8d895fd30c4c1?pvs=21"),
    WeekPlan(17, "Cierre: portafolio y prácticas", "1–7 feb 2027", LocalDate.of(2027, 2, 1), LocalDate.of(2027, 2, 7), "Cierre", listOf(
        "Elegir una capacidad de IA bien implementada antes que muchos prototipos.",
        "Publicar README, diagrama, decisiones, pruebas y variables de entorno.",
        "Hacer simulacros SQL, Java/Spring y explicación del proyecto.",
        "Preparar CV, GitHub y demo de cinco minutos."
    ), "Portafolio presentable y tres simulacros completados.", "https://app.notion.com/p/Cierre-portafolio-y-preparaci-n-para-pr-cticas-b1738607be254582afd25f4a7dbf73e3?pvs=21")
)

private val courseCatalog = listOf(
    CourseInfo("SQL y Bases de Datos desde Cero", "17 h 24 min", "28–36 h", "Diseñar una BD y dominar JOINs, subconsultas, CTEs, índices, vistas y consultas sin copiar; terminar Bookify."),
    CourseInfo("Git + GitHub", "11 h 36 min", "16–20 h", "Ramas, commits limpios, pull requests, conflictos, issues, Projects y README profesional."),
    CourseInfo("Java: Explora el lenguaje", "15 h", "28–35 h", "POO, colecciones, JSON, MVC y ejercicios independientes."),
    CourseInfo("Java avanzado", "25 h 30 min", "40–50 h", "Generics, Streams, Optional, JDBC, pools, hilos, Executors y RxJava."),
    CourseInfo("Spring Boot: guía definitiva", "36 h", "65–80 h", "API monolítica con JPA, validación, JWT, seguridad, testing y Docker."),
    CourseInfo("Docker", "13 h 54 min", "22–28 h", "Dockerfile, Compose, redes, volúmenes, multi-stage builds y publicación."),
    CourseInfo("Patrones de arquitectura", "20 h 30 min", "35–45 h", "Refactorizar con Clean/Hexagonal, DDD y decisiones de diseño justificadas."),
    CourseInfo("Spring Boot 4: Microservicios", "21 h", "35–45 h", "Comunicación entre servicios, RabbitMQ, Gateway, resiliencia y Kubernetes inicial."),
    CourseInfo("Amazon AWS básico", "13 h 36 min", "22–30 h", "IAM, redes, cómputo, almacenamiento, base de datos y despliegue sencillo; verificar cambios porque es un curso antiguo."),
    CourseInfo("Spring AI", "18 h 30 min", "35–45 h", "Gemini, tool calling, RAG, agentes, MCP y despliegue desde Java."),
    CourseInfo("Ingeniería de LLM", "42 h 38 min", "75–95 h", "APIs, inferencia, RAG, modelos abiertos, Hugging Face, Gradio, evaluación y prototipos; especialización, no sustituto de fundamentos.")
)

private val interviewTopics = listOf(
    InterviewTopic("poo", "Los 4 pilares de POO", "La POO organiza el código en objetos que reúnen datos y comportamiento. Sus pilares son encapsulación, abstracción, herencia y polimorfismo.", "Ayuda a dividir el sistema en responsabilidades y a cambiar una parte sin tener que reescribirlo todo.", "En una app de tienda, Producto mantiene sus datos y métodos; distintas formas de pago pueden compartir una interfaz.", "La encapsulación protege el estado; la abstracción expone lo importante; la herencia reutiliza y especializa; el polimorfismo permite tratar implementaciones distintas mediante el mismo contrato.", "¿Cuándo evitarías herencia?", "Cuando crea acoplamiento o la relación no es realmente un tipo de; composición suele ser más flexible."),
    InterviewTopic("interface", "Interfaz vs. clase abstracta", "Una interfaz define un contrato que distintas clases pueden implementar. Una clase abstracta puede compartir estado y comportamiento base, además de dejar métodos abstractos.", "La decisión depende de si necesito principalmente un contrato o una base compartida.", "PasarelaPago puede ser una interfaz; una clase abstracta Documento podría compartir validaciones comunes.", "Una clase puede implementar varias interfaces, pero extender una sola clase. Uso interfaz para capacidades/contratos y abstracta cuando varias subclases comparten una implementación fundamental.", "¿Qué usarías para varios proveedores de pago?", "Una interfaz PasarelaPago con implementaciones independientes por proveedor."),
    InterviewTopic("collections", "Collections de Java", "List mantiene el orden y permite elementos repetidos; Set evita duplicados; Map asocia claves con valores.", "Elegir bien la estructura simplifica las operaciones y evita trabajo innecesario.", "ArrayList para mostrar productos en orden, HashSet para IDs únicos y HashMap para buscar un producto por su código.", "También compararía coste y semántica: ArrayList da acceso por índice rápido; HashSet/HashMap suelen ofrecer búsqueda promedio constante, según hash y distribución.", "¿Por qué usarías Set?", "Cuando la unicidad importa y quiero comprobar pertenencia sin gestionar duplicados manualmente."),
    InterviewTopic("streams", "Streams y programación funcional", "Stream permite procesar colecciones declarando operaciones como filter, map y collect, sin describir manualmente cada paso.", "Puede hacer transformaciones legibles, aunque no siempre es mejor que un bucle simple.", "Filtrar pedidos pagados y obtener una lista con sus importes.", "filter selecciona, map transforma, sorted ordena y collect materializa un resultado. Evitaría efectos secundarios y streams excesivamente complejos.", "¿Stream modifica la lista original?", "No por sí mismo; las operaciones intermedias construyen un pipeline y las terminales producen el resultado, salvo que yo introduzca efectos secundarios."),
    InterviewTopic("spring-api", "Arquitectura de una API Spring Boot", "Normalmente separo controller, service y repository: el controller recibe HTTP, service contiene reglas de negocio y repository maneja persistencia.", "Las capas hacen el código más testeable y evitan mezclar HTTP, negocio y base de datos.", "Un POST crea un DTO, el servicio valida reglas, el repositorio guarda la entidad y el controller devuelve 201 Created.", "Además usaría DTOs, validación, manejo centralizado de errores, configuración externa y pruebas. No expondría la entidad de base de datos directamente como contrato público.", "¿Dónde pondrías una regla de negocio?", "En el servicio o dominio, no en el controller ni en el repositorio."),
    InterviewTopic("security", "Spring Security y JWT", "Spring Security aplica autenticación y autorización. JWT puede transportar claims firmados para que una API valide una sesión sin guardar una sesión tradicional en el servidor.", "La autenticación responde quién eres; la autorización qué puedes hacer.", "Un usuario inicia sesión, recibe un token y lo envía en Authorization: Bearer para consultar una ruta protegida.", "Validaría firma, expiración y claims, limitaría permisos por rol y no guardaría secretos dentro del token. JWT no cifra automáticamente su contenido.", "¿Qué haces si el token expira?", "La API lo rechaza; el cliente debe volver a autenticarse o usar un flujo seguro de renovación."),
    InterviewTopic("testing", "JUnit 5 y Mockito", "JUnit estructura y ejecuta pruebas; Mockito sustituye dependencias para aislar la unidad que quiero probar.", "Las pruebas verifican comportamiento y ayudan a cambiar código con menos miedo.", "Pruebo un servicio con un repositorio mock y verifico el resultado cuando el repositorio devuelve un producto.", "Probaría casos normales, límites y errores. Usaría mocks cuando necesito aislar una dependencia y pruebas de integración para verificar que componentes reales funcionan juntos.", "¿Mockearías la base de datos en una prueba de integración?", "No como regla: una integración busca comprobar la interacción real con infraestructura o una versión de prueba de ella."),
    InterviewTopic("sql-mongo", "MySQL vs. MongoDB", "MySQL es relacional y trabaja con tablas y relaciones. MongoDB almacena documentos, lo que puede adaptarse a estructuras anidadas y cambiantes.", "Primero elegiría según consultas, consistencia, relaciones y operación; no por moda.", "Un sistema de facturas con relaciones fuertes puede encajar bien en SQL; documentos de catálogo con atributos variables podrían encajar en MongoDB.", "SQL no significa rígido para todo ni MongoDB significa sin esquema. Ambos requieren diseño, índices, validación y entender las consultas principales.", "¿Migrarías a MongoDB solo porque escala?", "No; mediría el problema primero. Cambiar el modelo tiene coste y no garantiza mejor rendimiento."),
    InterviewTopic("solid", "SOLID y patrones", "SOLID es un conjunto de principios para reducir acoplamiento y facilitar cambios. Los patrones son soluciones conocidas a problemas de diseño que se repiten.", "Ayudan cuando resuelven una necesidad real, pero aplicarlos por moda añade complejidad.", "Strategy permite cambiar el algoritmo de scraping por tienda sin llenar una clase de if/else.", "Explicaría el problema, los trade-offs y por qué el diseño facilita pruebas o ampliaciones. No introduciría una abstracción si solo oculta una operación sencilla.", "¿Qué patrón usarías para varios scrapers?", "Strategy, con una implementación por tienda y una interfaz común."),
    InterviewTopic("ai", "RAG, agentes y MCP", "RAG recupera información relevante y la añade al contexto del modelo. Un agente decide pasos o herramientas para una tarea. MCP estandariza cómo una aplicación de IA descubre y usa herramientas o contexto externos.", "Los usaría cuando un modelo necesita datos actualizados o acciones controladas que no tiene por sí solo.", "Un asistente interno puede buscar documentación antes de contestar y usar una herramienta de consulta de estado con permisos limitados.", "Evaluaría calidad de recuperación, citas, límites, permisos, coste, latencia y fallos. No daría herramientas sensibles sin validación ni confirmación apropiada.", "¿RAG garantiza que la respuesta sea correcta?", "No. Puede mejorar el contexto, pero debo evaluar recuperación, fidelidad de la respuesta y manejo de información no encontrada.")
)

private fun currentWeekFor(date: LocalDate): WeekPlan? = roadmap.firstOrNull { !date.isBefore(it.start) && !date.isAfter(it.end) }

private fun dailyTasks(date: LocalDate): List<Task> {
    if (date.dayOfWeek == DayOfWeek.SUNDAY) return listOf(
        Task("weekly_review", "Hice la revisión semanal", "Logros, bloqueos y un ajuste para la próxima semana."),
        Task("next_week", "Dejé claro el objetivo de mañana", "Una tarea concreta, no una lista interminable."),
        Task("rest_day", "Descansé sin culpa", "El descanso también forma parte del plan."),
        Task("nutri_fitia", "Registré mi alimentación en Fitia", "Sin compensar ni castigarme por un día imperfecto.")
    )
    val items = mutableListOf(
        Task("study_goal", "Definí un objetivo concreto", "Ejemplo: resolver 10 consultas JOIN."),
        Task("study_a", "Bloque A · teoría activa", "Hasta 60 min: tomar notas breves y escribir ejemplos."),
        Task("study_b", "Bloque B · implementar sin IA", "Hasta 120 min: intentar, leer errores y depurar."),
        Task("study_c", "Bloque C · ejercicio propio", "60–90 min: crear una variación propia."),
        Task("study_d", "Bloque D · pruebas y evidencia", "Pruebas, commit, README y registro de errores."),
        Task("study_recall", "Repasé sin consultar", "10 min: qué aprendí, qué no puedo hacer y qué sigue."),
        Task("interview_practice", "Practiqué una respuesta en voz alta", "Prueba una versión de 15, 30 y 60 segundos."),
        Task("nutri_fitia", "Registré mi alimentación en Fitia", "Usa tus metas como referencia, no como una prueba de perfección."),
        Task("sleep", "Protegí mi sueño", "Objetivo habitual: 7–9 horas; no compensar estudiando de madrugada.")
    )
    if (date.dayOfWeek == DayOfWeek.SATURDAY) {
        items[1] = Task("study_a", "Repaso activo de la semana", "Cerrar vacíos y rehacer ejemplos sin el video." )
        items[2] = Task("study_b", "Examen práctico sin IA", "Construir o resolver desde una carpeta limpia." )
        items[3] = Task("study_c", "Cerré el entregable semanal", "Algo ejecutable que pueda mostrar y explicar." )
    }
    return items
}

private fun workoutFor(date: LocalDate): Workout? = when (date.dayOfWeek) {
    DayOfWeek.TUESDAY -> Workout("Superior A", "Pecho, espalda, hombros y brazos", listOf(
        Exercise("bench", "Press banca", "4 × 8–10"),
        Exercise("row", "Remo", "4 × 8–10"),
        Exercise("military", "Press militar", "3 × 10"),
        Exercise("pulldown", "Jalón o dominadas asistidas", "3 × 10"),
        Exercise("curl", "Curl de bíceps", "2 × 12"),
        Exercise("triceps", "Extensión de tríceps", "2 × 12")
    ))
    DayOfWeek.WEDNESDAY -> Workout("Inferior A", "Pierna y core", listOf(
        Exercise("squat", "Sentadilla", "4 × 8–10"),
        Exercise("rdl", "Peso muerto rumano", "3 × 10"),
        Exercise("lunges", "Zancadas por pierna", "3 × 10"),
        Exercise("calves", "Elevación de talones", "3 × 15"),
        Exercise("plank", "Plancha", "3 × 30–45 s")
    ))
    DayOfWeek.THURSDAY -> Workout("Superior B", "Pecho, espalda, hombros y brazos", listOf(
        Exercise("incline", "Press inclinado", "4 × 10"),
        Exercise("one_row", "Remo a una mano", "4 × 10"),
        Exercise("lateral", "Elevaciones laterales", "3 × 12–15"),
        Exercise("dips", "Fondos en banco", "3 × 12"),
        Exercise("hammer", "Curl martillo", "2 × 12"),
        Exercise("cable_tri", "Tríceps en polea", "2 × 12")
    ))
    DayOfWeek.FRIDAY -> Workout("Inferior B", "Pierna y core", listOf(
        Exercise("press", "Sentadilla búlgara o prensa", "4 × 10"),
        Exercise("deadlift", "Peso muerto convencional", "3 × 10"),
        Exercise("hip", "Hip thrust", "3 × 12"),
        Exercise("hamstring", "Curl femoral o RDL", "3 × 12"),
        Exercise("calves", "Elevación de talones", "3 × 15"),
        Exercise("leg_raise", "Elevación de piernas", "3 × 15")
    ))
    else -> null
}

private fun prettyDate(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", Spanish))
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Spanish) else it.toString() }

private fun monthTitle(month: YearMonth): String = month.atDay(1)
    .format(DateTimeFormatter.ofPattern("MMMM yyyy", Spanish))
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Spanish) else it.toString() }

private fun dateKey(date: LocalDate): String = date.toString()
private fun checkKey(scope: String, id: String): String = "$scope|$id"
private fun isChecked(prefs: SharedPreferences, scope: String, id: String): Boolean = prefs.getBoolean(checkKey(scope, id), false)
private fun checkedCount(prefs: SharedPreferences, scope: String, tasks: List<Task>): Int = tasks.count { isChecked(prefs, scope, it.id) }
private fun hasDateActivity(prefs: SharedPreferences, date: LocalDate): Boolean = prefs.all.any { (key, value) -> key.startsWith("${dateKey(date)}|") && value == true }

@Composable
private fun RutaApp(prefs: SharedPreferences) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var calendarMonth by remember { mutableStateOf(YearMonth.from(LocalDate.now())) }
    var revision by remember { mutableIntStateOf(0) }

    val toggle: (String, String) -> Unit = { scope, id ->
        val key = checkKey(scope, id)
        prefs.edit().putBoolean(key, !prefs.getBoolean(key, false)).apply()
        revision++
    }
    val saveText: (String, String) -> Unit = { key, value ->
        prefs.edit().putString(key, value).apply()
        revision++
    }

    androidx.compose.material3.Scaffold(
        containerColor = Page,
        bottomBar = {
            NavigationBar(containerColor = White, contentColor = Ink) {
                val items = listOf(
                    Triple("Hoy", Icons.Filled.Home, 0),
                    Triple("Ruta", Icons.Filled.MenuBook, 1),
                    Triple("Gym", Icons.Filled.FitnessCenter, 2),
                    Triple("Nutri", Icons.Filled.Restaurant, 3),
                    Triple("Entrevista", Icons.Filled.RecordVoiceOver, 4)
                )
                items.forEach { (label, icon, index) ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { androidx.compose.material3.Icon(icon, contentDescription = label) },
                        label = { Text(label, maxLines = 1, fontSize = 10.sp) },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (selectedTab) {
                0 -> HomeScreen(
                    prefs = prefs,
                    revision = revision,
                    selectedDate = selectedDate,
                    calendarMonth = calendarMonth,
                    onDateSelected = { selectedDate = it; calendarMonth = YearMonth.from(it) },
                    onMonthChanged = { calendarMonth = it },
                    onToggle = toggle,
                    onToday = { selectedDate = LocalDate.now(); calendarMonth = YearMonth.from(LocalDate.now()) }
                )
                1 -> StudyScreen(prefs, revision, onToggle = toggle)
                2 -> GymScreen(prefs, revision, selectedDate, onDateSelected = { selectedDate = it; calendarMonth = YearMonth.from(it) }, onToggle = toggle)
                3 -> NutritionScreen(prefs, revision, selectedDate, onToggle = toggle, onSaveText = saveText)
                else -> InterviewScreen(prefs, revision, onToggle = toggle, onSaveText = saveText)
            }
        }
    }
}

@Composable
private fun AppPage(
    eyebrow: String,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(eyebrow.uppercase(), color = GreenDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
        Text(title, color = Ink, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 32.sp)
        Text(subtitle, color = Muted, fontSize = 14.sp, lineHeight = 20.sp)
        content()
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun Panel(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
    }
}

@Composable
private fun SectionTitle(title: String, rightText: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(title, color = Ink, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        if (rightText != null) Text(rightText, color = GreenDark, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ProgressLine(progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(Line)) {
        if (progress > 0f) Box(Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(8.dp).clip(CircleShape).background(Green))
    }
}

@Composable
private fun CheckRow(
    title: String,
    detail: String = "",
    checked: Boolean,
    onToggle: () -> Unit,
    compact: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable(onClick = onToggle).padding(vertical = if (compact) 2.dp else 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = { onToggle() })
        Column(Modifier.weight(1f).padding(end = 6.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = if (checked) Muted else Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp)
            if (detail.isNotBlank()) Text(detail, color = Muted, fontSize = 12.sp, lineHeight = 16.sp)
        }
    }
}

@Composable
private fun StatTile(label: String, value: String, sub: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(value, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Muted)
            Text(sub, fontSize = 10.sp, color = GreenDark, lineHeight = 13.sp)
        }
    }
}

@Composable
private fun HomeScreen(
    prefs: SharedPreferences,
    revision: Int,
    selectedDate: LocalDate,
    calendarMonth: YearMonth,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onToggle: (String, String) -> Unit,
    onToday: () -> Unit
) {
    val tasks = dailyTasks(selectedDate)
    val scope = dateKey(selectedDate)
    val done = checkedCount(prefs, scope, tasks)
    val completion = if (tasks.isEmpty()) 0f else done.toFloat() / tasks.size.toFloat()
    val activeDays = (0..6).count { offset ->
        val day = LocalDate.now().minusDays(offset.toLong())
        hasDateActivity(prefs, day)
    }
    val week = currentWeekFor(selectedDate)
    val today = selectedDate == LocalDate.now()
    AppPage(
        eyebrow = "Mi Ruta 2027 · seguimiento personal",
        title = if (today) "Vamos paso a paso, Kevin." else prettyDate(selectedDate),
        subtitle = "Estudio, entrenamiento y hábitos en un solo lugar. Marca lo que hiciste; no busques un día perfecto, busca constancia."
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Ink)
        ) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("OBJETIVO · FEBRERO 2027", color = Color(0xFFB8EFB0), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = .8.sp)
                        Text("Prepararte para prácticas", color = White, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 25.sp)
                    }
                    Box(Modifier.size(48.dp).clip(CircleShape).background(Green), contentAlignment = Alignment.Center) {
                        androidx.compose.material3.Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = White, modifier = Modifier.size(29.dp))
                    }
                }
                Text(
                    week?.let { "Semana ${it.number} · ${it.title}" } ?: "Semana de recuperación y repaso",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text("Ver menos, programar más. Implementa sin IA y úsala después para revisar lo que ya intentaste.", color = Color(0xFFD6DDD4), fontSize = 12.sp, lineHeight = 17.sp)
                ProgressLine(completion, Modifier.padding(top = 2.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("${(completion * 100).toInt()}% del día marcado", color = White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("$done de ${tasks.size}", color = Color(0xFFB8EFB0), fontSize = 12.sp)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatTile("Tareas marcadas", "$done/${tasks.size}", "En la fecha elegida", Modifier.weight(1f))
            StatTile("Días activos", "$activeDays/7", "Últimos siete días", Modifier.weight(1f))
            StatTile("Entrenas", "4 días", "Martes a viernes", Modifier.weight(1f))
        }

        CalendarCard(
            month = calendarMonth,
            selectedDate = selectedDate,
            prefs = prefs,
            revision = revision,
            onDateSelected = onDateSelected,
            onMonthChanged = onMonthChanged
        )

        Panel {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Checklist del día", "${(completion * 100).toInt()}%")
            }
            Text(prettyDate(selectedDate), color = Muted, fontSize = 12.sp)
            if (!today) {
                OutlinedButton(onClick = onToday, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Text("Volver a hoy")
                }
            }
            tasks.forEach { task ->
                CheckRow(
                    title = task.title,
                    detail = task.detail,
                    checked = isChecked(prefs, scope, task.id),
                    onToggle = { onToggle(scope, task.id) }
                )
            }
            if (selectedDate.dayOfWeek in listOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
                Divider(color = Line)
                Text("También revisa la pestaña Gym y marca cada ejercicio al terminarlo.", color = GreenDark, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
        }

        Panel {
            SectionTitle("Reglas que te mantienen avanzando")
            BulletText("Intenta resolver primero; después revisa el error y prueba al menos dos hipótesis.")
            BulletText("Cierra cada semana con algo ejecutable que puedas mostrar y explicar.")
            BulletText("Si una semana falla, recupera el entregable; no castigues el sueño ni abandones el plan.")
            BulletText("La recompensa semanal (PS5, salida o actividad favorita) se gana con la puerta semanal completada; si una semana falla, recupera el entregable sin castigarte.")
        }
    }
}

@Composable
private fun CalendarCard(
    month: YearMonth,
    selectedDate: LocalDate,
    prefs: SharedPreferences,
    revision: Int,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit
) {
    Panel {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            SectionTitle("Tu calendario")
            androidx.compose.material3.Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = GreenDark)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { onMonthChanged(month.minusMonths(1)) }) { Text("‹", fontSize = 24.sp, color = Ink) }
            Text(monthTitle(month), fontSize = 16.sp, color = Ink, fontWeight = FontWeight.Bold)
            TextButton(onClick = { onMonthChanged(month.plusMonths(1)) }) { Text("›", fontSize = 24.sp, color = Ink) }
        }
        val weekdayLabels = listOf("L", "M", "X", "J", "V", "S", "D")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            weekdayLabels.forEach { label ->
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(label, color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        val offset = month.atDay(1).dayOfWeek.value - 1
        val cellCount = ((offset + month.lengthOfMonth() + 6) / 7) * 7
        repeat(cellCount / 7) { rowIndex ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(7) { columnIndex ->
                    val cellIndex = rowIndex * 7 + columnIndex
                    val number = cellIndex - offset + 1
                    if (number in 1..month.lengthOfMonth()) {
                        val date = month.atDay(number)
                        val chosen = date == selectedDate
                        val active = hasDateActivity(prefs, date)
                        Box(
                            modifier = Modifier.weight(1f).aspectRatio(0.9f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (chosen) Green else if (active) SoftGreen else Color.Transparent)
                                .border(if (date == LocalDate.now() && !chosen) 1.dp else 0.dp, if (date == LocalDate.now() && !chosen) Green else Color.Transparent, RoundedCornerShape(12.dp))
                                .clickable { onDateSelected(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(1.dp)) {
                                Text("$number", color = if (chosen) White else Ink, fontSize = 13.sp, fontWeight = if (chosen || active) FontWeight.Bold else FontWeight.Normal)
                                if (active) Box(Modifier.size(4.dp).clip(CircleShape).background(if (chosen) White else GreenDark))
                                else Spacer(Modifier.size(4.dp))
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f).aspectRatio(0.9f))
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(GreenDark))
            Text("Día con actividades guardadas", color = Muted, fontSize = 11.sp)
            Spacer(Modifier.weight(1f))
            Text("Toca una fecha para verla", color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun BulletText(text: String) {
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.padding(top = 6.dp).size(6.dp).clip(CircleShape).background(Green))
        Text(text, color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
    }
}

@Composable
private fun StudyScreen(prefs: SharedPreferences, revision: Int, onToggle: (String, String) -> Unit) {
    val current = currentWeekFor(LocalDate.now())
    AppPage(
        eyebrow = "Roadmap · septiembre 2026 a febrero 2027",
        title = "Tu ruta de backend e IA",
        subtitle = "Cada semana tiene tareas y un entregable. Marca el trabajo real, no solo los videos vistos. Si una semana se retrasa, termina el entregable antes de avanzar."
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = SoftGreen), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("ESTA SEMANA", color = GreenDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(current?.let { "Semana ${it.number} · ${it.title}" } ?: "Revisión y recuperación", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                Text(current?.deliverable ?: "Revisa el último entregable antes de elegir una tarea nueva.", fontSize = 13.sp, color = Ink, lineHeight = 18.sp)
                Text("Meta orientativa: 4–5 horas al día, 6 días por semana; prioriza calidad y consistencia.", fontSize = 12.sp, color = GreenDark)
            }
        }

        Panel {
            SectionTitle("Método para cada curso")
            val method = listOf(
                "Ver una lección y pausar para escribir el código a mano.",
                "Rehacer el ejemplo sin mirar el video.",
                "Resolver el ejercicio sin IA; leer el error y probar hipótesis.",
                "Crear una variación propia y registrar qué falló.",
                "Cerrar el bloque construyendo desde una carpeta vacía."
            )
            method.forEachIndexed { index, item -> BulletText("${index + 1}. $item") }
            Divider(color = Line)
            Text("Regla de prioridad", fontWeight = FontWeight.Bold, color = Ink, fontSize = 14.sp)
            Text("No recortes Spring Boot, testing, Docker ni el proyecto principal. Reduce Kubernetes a una introducción y posterga React si el backend aún no está estable. En IA, es mejor una capacidad bien hecha que muchos prototipos.", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
        }

        Panel {
            SectionTitle("Horario adaptado a tus clases y al metro")
            BulletText("Lunes: día libre de clases; buen momento para contenido nuevo, proyecto y planificación. No hay gym en la rutina base.")
            BulletText("Martes y jueves: clases 10:00–13:00. Lleva un snack para el trayecto; entrena al llegar, haz la comida fuerte sobre 15:30–16:00 y cena alrededor de las 20:00.")
            BulletText("Miércoles y viernes: clases 06:00–10:00. Toma algo ligero antes de salir, desayuna sobre 10:15, almuerza ligero cerca de 12:30 y entrena a las 14:00; la comida fuerte va después.")
            BulletText("Prioriza dormir 7–9 horas. Antes de los días con clase a las 06:00, acuéstate temprano y no intentes compensar con sesiones nocturnas largas.")
            BulletText("Sábado: repaso práctico y cierre semanal. Domingo: recuperación y ocio si la puerta semanal está cumplida.")
        }

        Panel {
            SectionTitle("Carga real de los cursos")
            Text("La ruta completa suma aproximadamente 235,6 horas de video y unas 455 horas reales con práctica, depuración, proyectos y repasos. Terminar el contenido no equivale a dominarlo.", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
            courseCatalog.forEach { course ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(course.title, color = Ink, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        SmallPill("Video ${course.video}")
                        SmallPill("Real ${course.real}")
                    }
                    Text(course.result, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
                    Divider(color = Line)
                }
            }
        }

        roadmap.forEach { week ->
            WeekCard(
                week = week,
                isCurrent = current?.number == week.number,
                prefs = prefs,
                revision = revision,
                onToggle = onToggle
            )
        }

        Panel {
            SectionTitle("Cursos que ya tienes")
            BulletText("Máster Completo Java: está al 99 %. Termina el 1 % restante y usa el resultado para validar huecos, sin repetirlo entero.")
            BulletText("Spring Boot & Spring Framework: continúa desde el 17 % y úsalo como curso principal de backend.")
            BulletText("JUnit 5 y Mockito: intégralos mientras construyes la API.")
            BulletText("Docker/Kubernetes y Spring Cloud/Eureka: primero monolito estable, después despliegue y separación de servicios.")
            BulletText("React + Spring Boot es opcional: solo después de estabilizar backend, pruebas y documentación.")
        }

        Panel {
            SectionTitle("Puerta para empezar a postular")
            BulletText("Diseñar una base de datos y escribir JOIN/CTE.")
            BulletText("Crear una API Spring Boot con persistencia, validación, JWT y pruebas.")
            BulletText("Ejecutar con Docker Compose y explicar las capas.")
            BulletText("Desplegar algo sencillo e integrar una función de IA con evaluación y límites.")
            BulletText("Explicar decisiones, compromisos y fallos sin atribuirlos a la IA.")
        }
    }
}

@Composable
private fun WeekCard(
    week: WeekPlan,
    isCurrent: Boolean,
    prefs: SharedPreferences,
    revision: Int,
    onToggle: (String, String) -> Unit
) {
    var expanded by rememberSaveable(week.number) { mutableStateOf(isCurrent) }
    val scope = "roadmap_${week.number}"
    val gateScope = "gate_${week.number}"
    val gateItems = listOf(
        "Cumplí el objetivo de horas realistas.",
        "Programé sin asistentes durante la implementación.",
        "Puedo explicar el código sin leer notas.",
        "Hay un entregable ejecutable en GitHub.",
        "Escribí una retrospectiva: logrado, bloqueo y ajuste."
    )
    val gateDone = gateItems.countIndexed { index, _ -> isChecked(prefs, gateScope, "gate_$index") }
    Panel {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable { expanded = !expanded }.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(if (isCurrent) Green else SoftGreen), contentAlignment = Alignment.Center) {
                Text(if (week.number == 17) "✓" else "${week.number}", color = if (isCurrent) White else GreenDark, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Semana ${week.number} · ${week.title}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Ink, lineHeight = 19.sp)
                Text("${week.dateLabel} · ${week.phase}", fontSize = 11.sp, color = Muted)
            }
            Text(if (expanded) "−" else "+", color = GreenDark, fontSize = 23.sp, fontWeight = FontWeight.Medium)
        }
        if (expanded) {
            Divider(color = Line)
            Text("Tareas", color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            week.tasks.forEachIndexed { index, task ->
                CheckRow(
                    title = task,
                    checked = isChecked(prefs, scope, "task_$index"),
                    onToggle = { onToggle(scope, "task_$index") },
                    compact = true
                )
            }
            Card(colors = CardDefaults.cardColors(containerColor = SoftGreen), shape = RoundedCornerShape(14.dp)) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text("ENTREGABLE MÍNIMO", color = GreenDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = .8.sp)
                    Text(week.deliverable, color = Ink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Puerta semanal", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Ink)
                Text("$gateDone/${gateItems.size}", color = GreenDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            ProgressLine(gateDone.toFloat() / gateItems.size.toFloat())
            gateItems.forEachIndexed { index, item ->
                CheckRow(
                    title = item,
                    checked = isChecked(prefs, gateScope, "gate_$index"),
                    onToggle = { onToggle(gateScope, "gate_$index") },
                    compact = true
                )
            }
            if (gateDone == gateItems.size) {
                Text("Semana cerrada con evidencia. ¡Buen trabajo!", color = GreenDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            if (week.notionUrl.isNotBlank()) {
                val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
                OutlinedButton(
                    onClick = { uriHandler.openUri(week.notionUrl) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(13.dp)
                ) { Text("Abrir página de Notion") }
            }
        }
    }
}

private inline fun <T> Iterable<T>.countIndexed(predicate: (Int, T) -> Boolean): Int {
    var total = 0
    forEachIndexed { index, value -> if (predicate(index, value)) total++ }
    return total
}

@Composable
private fun GymScreen(
    prefs: SharedPreferences,
    revision: Int,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onToggle: (String, String) -> Unit
) {
    val workout = workoutFor(selectedDate)
    val scope = dateKey(selectedDate)
    AppPage(
        eyebrow = "Entrenamiento · martes a viernes",
        title = workout?.title ?: "Día de recuperación",
        subtitle = if (workout == null) "Hoy no hay sesión programada en la rutina base. Descansar es parte del progreso; retoma según el calendario." else workout.focus
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { onDateSelected(selectedDate.minusDays(1)) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("‹ Día anterior") }
            OutlinedButton(onClick = { onDateSelected(selectedDate.plusDays(1)) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("Día siguiente ›") }
        }
        Panel {
            SectionTitle(prettyDate(selectedDate))
            if (workout == null) {
                Card(colors = CardDefaults.cardColors(containerColor = SoftGreen), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Día libre", color = GreenDark, fontWeight = FontWeight.Bold)
                        Text("Lunes, sábado y domingo son de descanso en la distribución base. Una caminata suave es opcional; no necesitas compensar sesiones perdidas.", color = Ink, fontSize = 13.sp, lineHeight = 18.sp)
                    }
                }
            } else {
                val allItems = listOf(Exercise("warmup", "Calentamiento", "5–10 min")) + workout.exercises + Exercise("progression", "Registré pesos y repeticiones", "Progresar poco a poco")
                val done = allItems.count { isChecked(prefs, scope, "gym_${it.id}") }
                Text("$done de ${allItems.size} marcados", color = GreenDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                ProgressLine(done.toFloat() / allItems.size.toFloat())
                allItems.forEach { exercise ->
                    CheckRow(
                        title = exercise.name,
                        detail = exercise.sets,
                        checked = isChecked(prefs, scope, "gym_${exercise.id}"),
                        onToggle = { onToggle(scope, "gym_${exercise.id}") }
                    )
                }
            }
        }
        Panel {
            SectionTitle("Reglas del entrenamiento")
            BulletText("Calienta 5–10 minutos y usa una técnica controlada.")
            BulletText("Descansa 60–90 segundos entre series; toma más tiempo si lo necesitas para mantener buena técnica.")
            BulletText("Busca progresar gradualmente: una repetición más o un poco de peso, sin sacrificar ejecución.")
            BulletText("Si tu amigo practicante te entrega una rutina ajustada, esa rutina tiene prioridad sobre esta plantilla.")
            BulletText("El músculo depende del entrenamiento progresivo, suficiente comida y recuperación; no necesitas llegar al fallo en todo.")
        }
        Panel {
            SectionTitle("Registro rápido")
            Text("Después de la sesión, anota en tu registro de entrenamiento el peso usado y las repeticiones de cada ejercicio. La casilla indica que lo hiciste; el detalle sirve para medir progreso.", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
        }
    }
}

@Composable
private fun NutritionScreen(
    prefs: SharedPreferences,
    revision: Int,
    selectedDate: LocalDate,
    onToggle: (String, String) -> Unit,
    onSaveText: (String, String) -> Unit
) {
    val scope = dateKey(selectedDate)
    var weightInput by remember(selectedDate) { mutableStateOf(prefs.getString("weight_$selectedDate", "") ?: "") }
    val weightEntries = prefs.all.mapNotNull { (key, value) ->
        if (!key.startsWith("weight_")) null
        else {
            val rawDate = key.removePrefix("weight_")
            val date = runCatching { LocalDate.parse(rawDate) }.getOrNull()
            val weight = (value as? String)?.toFloatOrNull()
            if (date != null && weight != null) date to weight else null
        }
    }.sortedByDescending { it.first }

    AppPage(
        eyebrow = "Nutrición · ganancia muscular controlada",
        title = "Come para progresar",
        subtitle = "Usa Fitia como registro y estas cifras como punto de partida. No necesitas acertar cada gramo; observa la tendencia del peso y el rendimiento."
    ) {
        Panel {
            SectionTitle("Metas orientativas de Fitia", "Balanceado")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MacroTile("Energía", "2.700", "kcal/día", Modifier.weight(1f))
                MacroTile("Proteína", "122 g", "al día", Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MacroTile("Carbohidratos", "351 g", "al día", Modifier.weight(1f))
                MacroTile("Grasas", "90 g", "al día", Modifier.weight(1f))
            }
            Text("Rango verde orientativo de Fitia: 2.430–2.970 kcal. No hace falta clavar la cifra todos los días; la referencia de proteína y calorías se revisa si cambia tu peso, actividad u objetivo.", color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
        }
        Panel {
            SectionTitle("Hábitos de alimentación", prettyDate(selectedDate))
            CheckRow("Registré mis comidas en Fitia", "Marca esta casilla también desde Hoy; el estado se sincroniza.", isChecked(prefs, scope, "nutri_fitia"), { onToggle(scope, "nutri_fitia") })
            CheckRow("Incluí una fuente de proteína en las comidas principales", "Huevos, pollo, atún, pescado, carne, legumbres o yogur griego.", isChecked(prefs, scope, "nutri_protein"), { onToggle(scope, "nutri_protein") })
            CheckRow("Preparé un snack práctico para mis horarios", "Banano, yogur, huevos, atún con galletas, queso o arepa con huevo.", isChecked(prefs, scope, "nutri_snack"), { onToggle(scope, "nutri_snack") })
            CheckRow("Comí algo adecuado antes/después del gym cuando correspondía", "Con menos de una hora: algo ligero. Después, haz tu comida más grande.", isChecked(prefs, scope, "nutri_training"), { onToggle(scope, "nutri_training") })
        }
        Panel {
            SectionTitle("Comidas que encajan contigo")
            BulletText("Tortilla de maduro + 3 huevos + atún + queso: opción completa y alta en proteína.")
            BulletText("Pollo o pescado con arroz y plátano: buena comida principal.")
            BulletText("Snacks útiles: huevos duros, yogur griego, atún con galletas, queso moderado, maní, pollo desmechado, arepa con huevo o fruta.")
            BulletText("La avena no es obligatoria si no te gusta. La proteína en polvo tampoco es necesaria si llegas a la meta con comida.")
            BulletText("Deja los chicharrones de harina, gaseosas y galletas dulces como algo ocasional; el queso puede complementar, pero no necesita ser tu base de proteína.")
        }
        Panel {
            SectionTitle("Peso semanal")
            Text("Pésate una vez por semana, en ayunas, sin zapatos y en la misma báscula. Usa la tendencia, no una medida aislada; dos básculas pueden dar valores distintos.", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
            Text("Peso del día seleccionado (kg)", color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            OutlinedTextField(
                value = weightInput,
                onValueChange = { value -> weightInput = value.replace(',', '.').filter { it.isDigit() || it == '.' }.let { text ->
                    val dotIndex = text.indexOf('.')
                    if (dotIndex >= 0) text.take(dotIndex + 1) + text.drop(dotIndex + 1).replace(".", "") else text
                } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej. 70.4") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(14.dp)
            )
            Button(
                onClick = {
                    val value = weightInput.toFloatOrNull()
                    if (value != null && value in 30f..250f) {
                        onSaveText("weight_$selectedDate", String.format(Locale.US, "%.1f", value))
                        if (!isChecked(prefs, scope, "weight_logged")) onToggle(scope, "weight_logged")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenDark)
            ) { Text("Guardar peso de esta fecha", color = White) }
            if (weightEntries.isNotEmpty()) {
                Divider(color = Line)
                Text("Últimos registros", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Ink)
                weightEntries.take(5).forEach { (date, weight) ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(date.format(DateTimeFormatter.ofPattern("d MMM yyyy", Spanish)), color = Muted, fontSize = 12.sp)
                        Text("${String.format(Locale.US, "%.1f", weight)} kg", color = Ink, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
        Panel {
            SectionTitle("Mercado base")
            Text("Presupuesto de referencia compartido: $150.000–177.000 para D1/Ara. Los precios cambian; úsalo para priorizar, no como garantía del costo actual.", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
            BulletText("Prioriza huevos, pechuga de pollo, atún, lentejas/fríjoles, queso en porción moderada, aguacate y maní.")
            BulletText("Reposiciones que faltaban en la última compra: pechuga, tomate y yogur griego.")
        }
        Panel {
            SectionTitle("Cómo ajustar sin improvisar")
            BulletText("La ganancia buscada era gradual: observa una tendencia aproximada de 0,25–0,5 kg por semana, no saltos de un día.")
            BulletText("Si pasan dos semanas sin cambio en la tendencia, revisa el registro y considera un ajuste pequeño de 150–200 kcal; no cambies todo por una sola pesada.")
            BulletText("Prioridad elegida: superávit moderado hasta alrededor de febrero y luego valorar una fase de definición controlada. Los 72 kg eran una referencia provisional, no una obligación; revisa la tendencia con tu báscula actual.")
            BulletText("La cara y el abdomen no se pueden definir de forma localizada. Evita restricciones extremas de solo agua y café.")
        }
    }
}

@Composable
private fun SmallPill(text: String) {
    Box(Modifier.clip(RoundedCornerShape(8.dp)).background(Page).padding(horizontal = 8.dp, vertical = 5.dp)) {
        Text(text, color = GreenDark, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun MacroTile(label: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(containerColor = if (label == "Proteína") SoftGreen else Page)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(label, color = Muted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Text(value, color = Ink, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(unit, color = GreenDark, fontSize = 11.sp)
        }
    }
}

@Composable
private fun InterviewScreen(
    prefs: SharedPreferences,
    revision: Int,
    onToggle: (String, String) -> Unit,
    onSaveText: (String, String) -> Unit
) {
    AppPage(
        eyebrow = "Preparación para prácticas · respuestas naturales",
        title = "Entiende primero. Responde después.",
        subtitle = "No memorices discursos largos. La respuesta debe sonar como tú: directa, clara y con un ejemplo concreto si aporta."
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = Ink), shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("REGLA DE ORO", color = Color(0xFFB8EFB0), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text("Si puedes decirlo en 20 segundos, no lo digas en 90.", color = White, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 26.sp)
                Text("El entrevistador preguntará más si quiere profundidad. Practica en voz alta con tres niveles.", color = Color(0xFFD6DDD4), fontSize = 13.sp, lineHeight = 18.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    TimingChip("15 s")
                    TimingChip("30 s")
                    TimingChip("60 s")
                }
            }
        }

        Panel {
            SectionTitle("Estructura de una buena respuesta")
            ResponseStep("1", "Respuesta corta", "Lo que dirías oralmente, sin rodeos.")
            ResponseStep("2", "Por qué", "Dos a cuatro frases que muestran criterio.")
            ResponseStep("3", "Ejemplo", "Un caso simple que hayas construido o entendido.")
            ResponseStep("4", "Respuesta ampliada", "Solo si te piden más profundidad.")
            ResponseStep("5", "Repregunta", "Anticipa la siguiente pregunta y responde con naturalidad.")
        }

        Panel {
            SectionTitle("Plan de práctica")
            BulletText("Semana 1: presentación personal, entrevista vs. prueba técnica y explicación de tu proyecto.")
            BulletText("Semana 2: Java, POO, interfaz vs. abstracta, Collections y Streams.")
            BulletText("Semana 3: Spring Boot, capas, REST, Security/JWT, configuración y testing.")
            BulletText("Semana 4: SQL vs. MongoDB, SOLID, patrones y una explicación honesta de IA en desarrollo.")
            BulletText("Siempre: guarda tu respuesta en el banco personal y grábate o practica en voz alta.")
        }

        SectionTitle("Banco de temas", "${interviewTopics.count { isChecked(prefs, "interview_learned", it.id) }}/${interviewTopics.size} practicados")
        interviewTopics.forEach { topic ->
            InterviewTopicCard(topic, prefs, revision, onToggle, onSaveText)
        }

        Panel {
            SectionTitle("Presentación personal · estructura")
            Text("Prepara una respuesta de 30–45 segundos con este orden, usando tus propias palabras:", color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
            BulletText("Quién eres y qué estudias.")
            BulletText("Qué backend sabes construir hoy con Java/Spring Boot y bases de datos.")
            BulletText("Un proyecto concreto: problema, decisión técnica y resultado.")
            BulletText("Qué estás buscando aprender en las prácticas.")
            Text("Evita afirmar dominio total. Es más sólido explicar qué hiciste, qué aprendiste y qué mejorarías.", color = GreenDark, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun TimingChip(text: String) {
    Box(Modifier.clip(RoundedCornerShape(10.dp)).background(Color(0xFF2D362C)).padding(horizontal = 14.dp, vertical = 8.dp)) {
        Text(text, color = Color(0xFFB8EFB0), fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ResponseStep(number: String, title: String, detail: String) {
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(Modifier.size(28.dp).clip(CircleShape).background(SoftGreen), contentAlignment = Alignment.Center) {
            Text(number, color = GreenDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(detail, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun InterviewTopicCard(
    topic: InterviewTopic,
    prefs: SharedPreferences,
    revision: Int,
    onToggle: (String, String) -> Unit,
    onSaveText: (String, String) -> Unit
) {
    var expanded by rememberSaveable(topic.id) { mutableStateOf(false) }
    var myAnswer by rememberSaveable(topic.id) { mutableStateOf(prefs.getString("answer_${topic.id}", "") ?: "") }
    val practiced = isChecked(prefs, "interview_learned", topic.id)
    Panel {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable { expanded = !expanded }.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(Modifier.size(34.dp).clip(CircleShape).background(if (practiced) SoftGreen else Page), contentAlignment = Alignment.Center) {
                Text(if (practiced) "✓" else "?", color = GreenDark, fontWeight = FontWeight.ExtraBold)
            }
            Text(topic.title, modifier = Modifier.weight(1f), color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 18.sp)
            Text(if (expanded) "−" else "+", color = GreenDark, fontSize = 22.sp)
        }
        if (expanded) {
            Divider(color = Line)
            AnswerBlock("RESPUESTA CORTA · 15–20 s", topic.short)
            AnswerBlock("POR QUÉ", topic.why)
            AnswerBlock("EJEMPLO", topic.example)
            AnswerBlock("RESPUESTA AMPLIADA · 60 s", topic.extended)
            AnswerBlock("REPREGUNTA PROBABLE", topic.followUp)
            AnswerBlock("POSIBLE RESPUESTA", topic.followUpAnswer)
            Text("Tu versión (escribe cómo lo dirías tú)", fontSize = 13.sp, color = Ink, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = myAnswer,
                onValueChange = { myAnswer = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Escribe una respuesta natural, con tus palabras…") },
                minLines = 3,
                maxLines = 6,
                shape = RoundedCornerShape(14.dp)
            )
            Button(
                onClick = { onSaveText("answer_${topic.id}", myAnswer) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenDark),
                shape = RoundedCornerShape(13.dp)
            ) { Text("Guardar mi respuesta", color = White) }
            CheckRow(
                title = "La practiqué en voz alta",
                detail = "Marca cuando puedas explicarla sin leer la pantalla.",
                checked = practiced,
                onToggle = { onToggle("interview_learned", topic.id) },
                compact = true
            )
        }
    }
}

@Composable
private fun AnswerBlock(label: String, answer: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(label, color = GreenDark, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = .6.sp)
        Text(answer, color = Ink, fontSize = 13.sp, lineHeight = 18.sp)
    }
}
