# JobTask API

A simple Spring Boot project with JWT-based authentication and role-based authorization.  

## Entities

- **User**  
  - Handles authentication: `register`, `login`, `refresh token`.
  - Roles: `USER`, `ADMIN`.

- **Product**  
  - Operations: `getAll`, `getOne`, `create`, `update`, `patch`, `delete`.
  - `USER` can **view all products** and **buy products**.  
  - `ADMIN` can perform **all operations**.

## Security

- JWT-based authentication.  
- Role-based access control:  
  - `USER`: can view products and buy.  
  - `ADMIN`: full access.

## Endpoints

### Auth

- `POST /api/auth/register` – register a new user.  
- `POST /api/auth/login` – login and receive JWT token.  
- `POST /api/auth/refresh` – refresh JWT token.  

### Products

- `GET /api/products/all` – get all products (`USER` and `ADMIN`).  
- `GET /api/products/{id}` – get one product (`USER` and `ADMIN`).  
- `POST /api/products` – create a product (`ADMIN` only).  
- `PATCH /api/products/{id}/buy` – buy a product (`USER` only).  
- `PUT /api/products/{id}` – update a product (`ADMIN` only).  
- `DELETE /api/products/{id}` – delete a product (`ADMIN` only).

## Running

1. Clone the repository  
```bash
git clone <repo-url>
