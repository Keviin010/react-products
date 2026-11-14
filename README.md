# react-products

Interfaz de gestión de productos (CRUD) hecha con React y Vite, con CSS propio, conectada a una API de Spring Boot.

**Demo en línea:** https://react-products-sooty.vercel.app

El backend gratuito se apaga tras 15 minutos sin visitas. Si la página tarda en cargar, es el servidor despertando: espera hasta un minuto.

![React](https://img.shields.io/badge/React-18-%2361DAFB.svg?style=flat&logo=react&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-6-%23646CFF.svg?style=flat&logo=vite&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-3-%231572B6.svg?style=flat&logo=css3&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-1.7-5A29E4?style=flat)

Backend: [products-backend](https://github.com/Keviin010/products-backend) (Spring Boot y PostgreSQL).

## Qué hace

- Lista los productos con el precio en pesos colombianos dentro de una etiqueta.
- Muestra una vista previa de la etiqueta mientras escribes en el formulario.
- Crea, edita y elimina productos, con avisos de SweetAlert2 y confirmación antes de borrar.
- Valida el formulario antes de enviarlo y conserva lo escrito si el servidor falla.
- Busca productos por nombre.
- Si el servidor está dormido, lo indica y reintenta la conexión cada 4 segundos hasta por un minuto.
- Se adapta a computador y celular.

## Tecnologías

React 18, Vite, CSS propio, Axios y SweetAlert2. Las fuentes (Familjen Grotesk y Barlow Condensed) se instalan con npm, así que no dependen de ningún servicio externo.

## Cómo ejecutarlo en tu computador

1. Instala las dependencias:

```bash
npm install
```

2. Ejecuta la aplicación:

```bash
npm run dev
```

La aplicación queda en `http://localhost:5173` y se conecta al backend en `http://localhost:8080`.

## Dirección del backend

La dirección sale de la variable `VITE_API_URL`. Si no existe, usa `http://localhost:8080`. Para apuntar a otro servidor, crea un archivo `.env` en la raíz con esta línea:

```
VITE_API_URL=https://tu-backend.onrender.com
```

## Despliegue en Vercel

1. Importa este repositorio en Vercel. Detecta Vite solo y deja el Root Directory en `./`.
2. Abre Environment Variables y agrega `VITE_API_URL` con la dirección de tu backend en Render, sin barra al final.
3. Pulsa Deploy.
4. Si cambias la variable después, haz Redeploy para que se aplique.

## Rutas del backend

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/products` | Lista todos los productos |
| POST | `/api/products` | Crea un producto |
| PUT | `/api/products/{id}` | Actualiza un producto |
| DELETE | `/api/products/{id}` | Elimina un producto |

## Autor

Kevin Royo, [@Keviin010](https://github.com/Keviin010) · [LinkedIn](https://www.linkedin.com/in/kevin-royo-09a427216/)
