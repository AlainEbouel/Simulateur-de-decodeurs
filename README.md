# 📘 API Backend – Système de Gestion des Décodeurs

**Base URL** : `{{backend_address}}`  
**Authentification** : Toutes les routes sécurisées nécessitent une authentification HTTP Basic.

**Header requis** :
```http
Authorization: Basic base64(username:password)
```

## 👤 1. Lister les clients (par un admin)
- **Méthode** : `GET`  
- **URL** : `{{backend_address}}/admin/clients`  
- **Rôle requis** : `ADMIN`

### Exemple de réponse
```json
[
  {
    "id": 12,
    "name": "paradise_motel",
    "decoders": [
      { "id": 17, "ipAddress": "127.0.10.2", "channels": [] },
      { "id": 18, "ipAddress": "127.0.10.3", "channels": [] },
      { "id": 25, "ipAddress": "127.0.10.10", "channels": [] }
    ]
  },
  {
    "id": 13,
    "name": "sublime_motel",
    "decoders": [
      { "id": 19, "ipAddress": "127.0.10.4", "channels": [] },
      { "id": 20, "ipAddress": "127.0.10.5", "channels": [{ "id": 2, "name": "HBO" }] },
      { "id": 21, "ipAddress": "127.0.10.6", "channels": [{ "id": 2, "name": "HBO" }] }
    ]
  },
  {
    "id": 14,
    "name": "aventura_motel",
    "decoders": [
      { "id": 22, "ipAddress": "127.0.10.7", "channels": [] },
      { "id": 23, "ipAddress": "127.0.10.8", "channels": [{ "id": 2, "name": "HBO" }, { "id": 1, "name": "Netflix" }] },
      { "id": 24, "ipAddress": "127.0.10.9", "channels": [] }
    ]
  }
]
```

## ➕ 2. Création d’un client (par un admin)
- **Méthode** : `POST`  
- **URL** : `{{backend_address}}/admin/clients`  
- **Rôle requis** : `ADMIN`

### Corps de la requête
```json
{
  "name": "nom_du_client",
  "password": "mot_de_passe"
}
```

### Réponse
```json
{
  "id": 16,
  "name": "nom_du_client",
  "decoders": null
}
```

- `201 Created` : Client créé avec succès  
- `400 Bad Request` : Corps de requête invalide

## 🗑️ 3. Suppression d’un client (par un admin)
- **Méthode** : `DELETE`  
- **URL** : `{{backend_address}}/admin/clients/{clientId}`  
- **Rôle requis** : `ADMIN`

- `204 No Content` : Client supprimé  
- `404 Not Found` : Client non trouvé

## 📦 4. Lister les décodeurs disponibles (non assignés)
- **Méthode** : `GET`  
- **URL** : `{{backend_address}}/admin/decoders/unassigned`  
- **Rôle requis** : `ADMIN`

### Réponse
```json
["127.0.10.1", "127.0.10.12"]
```

## 📦 5. Assigner un décodeur à un client
- **Méthode** : `POST`  
- **URL** : `{{backend_address}}/admin/decoders`  
- **Rôle requis** : `ADMIN`  
- **IP valides** : `127.0.10.0` à `127.0.10.12`

### Corps de la requête
```json
{
  "ipAddress": "127.0.10.10",
  "client": { "id": 12 }
}
```

- `200 OK` : Décodeur assigné  
- `404 Not Found` : Client ou décodeur introuvable

## 🔄 6. Retirer un décodeur d’un client
- **Méthode** : `DELETE`  
- **URL** : `{{backend_address}}/admin/decoders/{decoderId}`  
- **Rôle requis** : `ADMIN`

- `204 No Content` : Décodeur retiré  
- `403 Forbidden`  
- `404 Not Found`

## 📄 7. Lister les décodeurs d’un client
- **Méthode** : `GET`  
- **URL** : `{{backend_address}}/client/decoders`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
[
  { "id": 19, "ipAddress": "127.0.10.4" },
  { "id": 20, "ipAddress": "127.0.10.5" }
]
```

## 📊 8. Obtenir l’état d’un décodeur
- **Méthode** : `GET`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/status`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
{
  "response": "OK",
  "state": "active",
  "lastRestart": "2025-04-19 15:22:47",
  "lastReinit": "2025-04-09 23:11:16"
}
```

## 🔁 9. Redémarrer un décodeur
- **Méthode** : `POST`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/reboot`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
{ "response": "OK" }
```

## ♻️ 10. Réinitialiser un décodeur
- **Méthode** : `POST`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/reinit`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
{ "response": "OK" }
```

## 📺 11. Lister les chaînes d’un décodeur
- **Méthode** : `GET`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/channels`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
[
  { "id": 1, "name": "Netflix" },
  { "id": 2, "name": "HBO" }
]
```

## ➕ 12. Ajouter une chaîne à un décodeur
- **Méthode** : `POST`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/add-channel/{channelId}`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
{ "response": "Channel added" }
```

## ➖ 13. Retirer une chaîne d’un décodeur
- **Méthode** : `DELETE`  
- **URL** : `{{backend_address}}/client/decoder/{decoderId}/remove-channel/{channelId}`  
- **Rôle requis** : `CLIENT`

### Réponse
```json
{ "response": "Channel removed" }
```