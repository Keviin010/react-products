# react-products

![React](https://img.shields.io/badge/React-18-%2361DAFB.svg?style=flat&logo=react&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-6-%23646CFF.svg?style=flat&logo=vite&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-%237952B3.svg?style=flat&logo=bootstrap&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-1.7-5A29E4?style=flat)
![Status](https://img.shields.io/badge/status-activo-brightgreen)

Frontend en React + Vite para gestión de productos (CRUD), conectado a un backend Spring Boot.

## Stack

**Frontend**
- React 18 · Vite · Bootstrap 5
- Axios · SweetAlert2

**Backend**
- Java 21 · Spring Boot · Spring Data JPA · MySQL

## Correr el proyecto

```bash
npm install
npm run dev
```

La app corre en `http://localhost:5173`

## Endpoints del backend

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/products` | Listar todos |
| POST | `/api/products` | Crear producto |
| PUT | `/api/products/{id}` | Actualizar producto |
| DELETE | `/api/products/{id}` | Eliminar producto |

Configura la URL en `src/services/productService.js`.

## Backend

Repositorio del backend en Spring Boot: [products-backend](https://github.com/Keviin010/products-backend)

## Autor

Kevin — [@Keviin010](https://github.com/Keviin010) · [LinkedIn](https://www.linkedin.com/in/kevin-royo-09a427216/)
