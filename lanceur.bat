@echo off
set "currentDir=%cd%"

rem Aller dans le répertoire du ProxyCacheServer et lancer l'exécutable
cd /d "%currentDir%\Servers\ProxyCacheServer\bin\Debug\"
powershell Start-Process -FilePath "ProxyCacheServer.exe" -Verb runAs

rem Attendre un moment pour permettre au ProxyCacheServer de démarrer avant de passer au serveur de routage
timeout /t 5 /nobreak

rem Aller dans le répertoire du RoutingServer et lancer l'exécutable
cd /d "%currentDir%\Servers\RoutingServer\bin\Debug\"
powershell Start-Process -FilePath "RoutingServer.exe" -Verb runAs

rem Attendre un moment pour permettre au RoutingServer de démarrer avant de passer au client
timeout /t 5 /nobreak

rem Aller dans le répertoire du Client et exécuter la commande Maven
cd /d "%currentDir%\Client\"
call mvn compile exec:java

pause
