# Trabajos de Final de Grado (UDS)
## Solución Tecnológica para Junta de Saneamiento Ambiental ubicado en la Ciudad de Caaguazú – Toro Blanco

Sistema desarrollado como parte del Trabajo Final de Grado en Ingeniería de Análisis de Sistemas Informáticos. Diseñado para digitalizar los procesos administrativos de la Junta de Saneamiento "Toro Blanco" en Caaguazú, Paraguay. Implementado en Java con MySQL, sigue el patrón MVC y utiliza Active Record para la gestión de datos.

# Requisitos previos

# Tutorial: Instalación de MySQL 8.4 y cómo importar una base de datos

## Requisitos previos
- Sistema operativo compatible (Windows, macOS o Linux).
- Acceso de administrador en el sistema.
- Conexión a internet estable.

---

## Paso 1: Descargar MySQL 8.4

1. Accede al sitio oficial de MySQL: [https://dev.mysql.com/downloads/](https://dev.mysql.com/downloads/).
2. Haz clic en la sección **MySQL Community Server**.
3. Selecciona la versión **8.4** (o la última disponible en la rama 8.4).
4. Elige el instalador adecuado para tu sistema operativo:
   - **Windows**: MySQL Installer (msi).
   - **macOS**: Paquete DMG.
   - **Linux**: Paquete DEB o RPM según la distribución.

---

## Paso 2: Instalar MySQL

### En Windows:
1. Ejecuta el instalador descargado (`mysql-installer-community-8.4.x.x.msi`).
2. Selecciona **Custom Installation** o **Developer Default** según tus necesidades.
3. Sigue las instrucciones del instalador para configurar:
   - Directorio de instalación.
   - Puerto (predeterminado: `3306`).
   - Contraseña para el usuario `root`.
4. Finaliza la instalación y asegúrte de que el servicio MySQL esté activo.

### En macOS:
1. Abre el archivo DMG descargado y sigue el asistente de instalación.
2. Configura la contraseña para el usuario `root` durante la instalación.
3. Usa el panel de preferencias de MySQL para iniciar/gestionar el servidor.

### En Linux:
#### Para distribuciones basadas en Debian/Ubuntu:
```bash
sudo apt update
sudo apt install mysql-server
```

#### Para distribuciones basadas en Red Hat/CentOS:
```bash
sudo dnf install mysql-server
```

1. Inicia el servicio MySQL:
```bash
sudo systemctl start mysqld
```
2. Configura la contraseña para el usuario `root` si es necesario:
```bash
sudo mysql_secure_installation
```

---

## Paso 3: Verificar la instalación

1. Abre un terminal o línea de comandos.
2. Conéctate al servidor MySQL:
```bash
mysql -u root -p
```
3. Ingresa la contraseña configurada para el usuario `root`.
4. Si ves el prompt de MySQL (`mysql>`), la instalación fue exitosa.

---

## Paso 4: Importar una base de datos desde un archivo `.sql`

1. Coloca el archivo `.sql` en una ubicación accesible (por ejemplo, `C:\import.sql` en Windows o `~/import.sql` en Linux/macOS). En este caso el archivo esta ubicado en la raiz del proyecto `db_backup.sql`

2. Accede a MySQL desde el terminal o línea de comandos:
```bash
mysql -u root -p
```

3. Sal del cliente MySQL escribiendo:
```sql
EXIT;
```

4. Importa el archivo `.sql` usando el siguiente comando:
```bash
mysql -u root -p < /ruta/a/tu/archivo.sql
```
   - Ejemplo en Windows:
     ```bash
     mysql -u root -p < C:\import.sql
     ```
   - Ejemplo en Linux/macOS:
     ```bash
     mysql -u root -p < ~/import.sql
     ```

5. Verifica la importación, en caso caso la base de datos tiene el nombre de `tfg`:
```bash
mysql -u root -p
USE nombre_de_tu_base_de_datos;
SHOW TABLES;
```

# Tutorial: Instalación del Java Runtime Environment (JRE)

## ¿Qué es el JRE?
El **Java Runtime Environment (JRE)** es un entorno necesario para ejecutar aplicaciones y programas desarrollados en Java. Incluye la máquina virtual de Java (JVM), bibliotecas y otras herramientas esenciales.

---

## Paso 1: Descargar el JRE

1. Visita el sitio oficial de Oracle: [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Busca la sección correspondiente a la versión más reciente de Java SE.
3. Descarga el archivo instalador del **JRE** compatible con tu sistema operativo:
   - **Windows**: `.exe`.
   - **macOS**: `.dmg`.
   - **Linux**: `.deb` o `.rpm` según la distribución.

---

## Paso 2: Instalar el JRE

### En Windows:
1. Abre el instalador descargado (`jre-x.x.x-windows-x64.exe`).
2. Sigue el asistente de instalación:
   - Selecciona el directorio de instalación o deja la opción predeterminada.
   - Haz clic en **Next** hasta completar la instalación.
3. Finaliza la instalación haciendo clic en **Close**.

### En macOS:
1. Abre el archivo DMG descargado.
2. Haz doble clic en el instalador y sigue las instrucciones del asistente.
3. Ingresa tu contraseña de administrador si se solicita.
4. Una vez completado, verifica la instalación desde el terminal (verificar más abajo).

### En Linux:
#### Para distribuciones basadas en Debian/Ubuntu:
1. Descarga el archivo `.deb`.
2. Abre una terminal y ejecuta:
   ```bash
   sudo dpkg -i jre-x.x.x-linux-x64.deb
   sudo apt-get install -f
   ```

#### Para distribuciones basadas en Red Hat/CentOS:
1. Descarga el archivo `.rpm`.
2. Abre una terminal y ejecuta:
   ```bash
   sudo rpm -ivh jre-x.x.x-linux-x64.rpm
   ```

---

## Paso 3: Verificar la instalación
1. Abre una terminal o línea de comandos.
2. Escribe el siguiente comando para verificar la versión instalada del JRE:
   ```bash
   java -version
   ```
   - Si la instalación fue exitosa, deberías ver algo como:
     ```
     java version "1.8.0_xxx"
     Java(TM) SE Runtime Environment (build 1.8.0_xxx-bxx)
     Java HotSpot(TM) 64-Bit Server VM (build xx.xx-bxx, mixed mode)
     ```
