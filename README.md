# CampusFind - JU Lost & Found

A simple Java Swing desktop application for campus lost-and-found reports.
Data is stored in local UTF-8 text files and image folders, with no database or server.

## Run the project

1. Open the project in Apache NetBeans with JDK 25.
2. Close any older running CampusFind windows.
3. Choose Clean and Build, then press F6.
4. Register an account or log in using email/student ID and password.

The project targets Java 24 bytecode and builds with JDK 25.
Command-line build: ant jar. Run: java -jar dist/Lost_Found.jar.

## Demonstration features

- Account creation, login, account details and logout.
- Dashboard with a dark navy sidebar.
- Lost and Found report forms, with campus location selection and optional photo.
- Browse Reports with search, Lost/Found filters, sorting, details and image preview.
- Matching Reports with explained text similarity scores.
- Finder-only Mark as Returned action.
- Persistent local text-file storage.

## Matching

The matching engine compares active, opposite-type reports and excludes found dates
earlier than the lost date. It normalizes words and a small synonym list such as
airpod/earbud/earphone and phone/mobile. A shared item-name word is required,
excluding common colors and materials.

The score uses name similarity (50), description similarity (20), category (15),
location (10) and date proximity (5). Suggestions require at least 40 points.
Scores describe text similarity, not proof of ownership. Photos are not analyzed.

## Editing the interface

Every active GUI page has a matching Java and .form file. Open the Java file and
select Design to edit labels, colors, controls and layout. Save before running.
Keep Java/form pairs together and preserve NetBeans guarded sections.
The established content size is 760 by 620.

## Local storage

Accounts are stored in data/users/, reports in data/reports/, and photos in data/images/.
Passwords are stored as salted PBKDF2-HMAC-SHA256 hashes, never plaintext.
The data folder is relative to the working directory; the optional JVM argument
-Dcampusfind.data.dir=PATH selects another folder.

Returned items stay browseable and are excluded from matching. A finder selects
their own active Found report in Browse Reports and clicks Mark as Returned.
The report saves the returned status, finder ID and UTC return timestamp.

Local data, build outputs and machine-specific IDE settings are excluded from Git.