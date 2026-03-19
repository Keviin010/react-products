# React + Vite
### react-products

> ⚠️ Proyecto en construcción — el backend aún está en desarrollo.

Frontend en React + Vite para gestión de productos (CRUD), conectado a un backend Spring Boot.

## Stack

**Frontend**
- React 18
- Vite
- Bootstrap 5
- Axios
- SweetAlert2

**Backend** *(en desarrollo)*
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL

## Correr el proyecto

```bash
npm install
npm run dev
```

## Backend esperado

```
GET    /api/products
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

Configura la URL en `src/services/productService.js`.

## Autor
Kevin — [@Keviin010](https://github.com/Keviin010)
