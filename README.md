# Assessment RSystems


## List all products in the database

```bash
curl -X 'GET' \
'http://localhost:8080/api/product' \
-H 'accept: */*'
```
### Sample Output
```json
[
  {
    "id": 1,
    "name": "Pen",
    "description": "Pen",
    "price": 10
  },
  {
    "id": 2,
    "name": "Pencil",
    "description": "Pencil",
    "price": 5
  },
  {
    "id": 3,
    "name": "Eraser",
    "description": "Eraser",
    "price": 2
  }
]
```


## Add an item to the cart

```bash
curl -X 'POST' \
  'http://localhost:8080/api/cart/1/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": 2,
  "quantity": 2
}'
```
### Sample Output
```json
[{
  "cartItemResponses": [
    {
      "product": {
        "id": 2,
        "name": "Pencil",
        "description": "Pencil",
        "price": 5
      },
      "quantity": 2
    }
  ],
  "cartId": 1
}]
```

## Get all items in the cart

```bash
curl -X 'GET' \
  'http://localhost:8080/api/cart/get/1' \
  -H 'accept: */*'
```
### Sample Output
```json
[
  {
    "cartItemResponses": [
      {
        "product": {
          "id": 2,
          "name": "Pencil",
          "description": "Pencil",
          "price": 5
        },
        "quantity": 2
      }
    ],
    "cartId": 1
  }
]
```

## Get all Shopping Carts from the database

```bash
curl -X 'GET' \
  'http://localhost:8080/api/cart/get' \
  -H 'accept: */*'
```
### Sample Output
```json
[
  {
    "cartItemResponses": [
      {
        "product": {
          "id": 2,
          "name": "Pencil",
          "description": "Pencil",
          "price": 5
        },
        "quantity": 2
      }
    ],
    "cartId": 1
  }
]
```
## Delete an item from the cart

```bash
curl -X 'DELETE' \
'http://localhost:8080/api/cart/delete/3/2' \
-H 'accept: */*'
```
### Sample Output
```
HTTP Status 200
```



## Tech

Application uses a number of open source projects to work properly:

- Java 17
- H2 Database
- lombok - for code generation
- springdoc-openapi-starter-webmvc-ui - for api documentations


# swagger-ui

http://localhost:8080/swagger-ui/index.html

