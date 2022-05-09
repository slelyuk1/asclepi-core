# Asclepi Core

## Development Guide

### How to configure local database

1. Install Postgres SQL to your local machine.
2. Run these commands:

```
sudo -u postgres psql
CREATE USER asclepi_admin WITH PASSWORD 'asclepi_admin_password';
CREATE DATABASE "asclepi-core" OWNER asclepi_admin;
```

## Rest API

[Link to REST API documentation](https://jupiter-solutions.github.io/asclepi-core/)
