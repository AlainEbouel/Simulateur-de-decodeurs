# 📘 API Backend – Système de Gestion des Décodeurs

**Base URL** : `http://localhost:8080`

## 🔐 Authentification

Toutes les routes sécurisées nécessitent une authentification HTTP Basic.

**Header requis :**

```
Authorization: Basic base64(username:password)
```

---

## 1. Lister les clients (par un admin)

- **Méthode** : `GET`
- **URL** : `http://localhost:8080/admin/clients`
- **Rôles autorisés** : `ADMIN`

**Exemple de réponse :**

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
    }
]
```

**Réponse** : `200 OK`

---

## 2. Création d’un client (par un admin)

- **Méthode** : `POST`
- **URL** : `http://localhost:8080/admin/clients`
- **Rôles autorisés** : `ADMIN`

**Body JSON :**

```json
{
  "name": "nom_du_client",
  "password": "mot_de_passe"
}
```

**Exemple de réponse :**

```json
{
  "id": 16,
  "name": "nom_du_client",
  "decoders": null
}
```

**Réponses :**
- `201 Created` : Client créé avec succès.
- `400 Bad Request` : Corps de requête invalide.

---

## 3. Suppression d’un client (par un admin)

- **Méthode** : `DELETE`
- **URL** : `http://localhost:8080/admin/clients/{clientId}`
- **Rôles autorisés** : `ADMIN`

**Réponses :**
- `204 No Content` : Client supprimé.
- `404 Not Found` : Client non trouvé.

---

## 4. Lister les décodeurs disponibles (par un admin)

- **Méthode** : `GET`
- **URL** : `http://localhost:8080/admin/decoders/unassigned`
- **Rôles autorisés** : `ADMIN`

**Réponse exemple :**

```json
[
  "127.0.10.1",
  "127.0.10.12"
]
```

**Réponse** : `200 OK`

---

(… et ainsi de suite pour les autres routes, comme dans le PDF…)