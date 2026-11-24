
# Inicio del backend Nostalgia
# Configura JWT e inicia Spring Boot

Set-Location $PSScriptRoot

# Generar una clave JWT segura para esta ejecución
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)

$env:JWT_SECRET = [Convert]::ToBase64String($bytes)

$rng.Dispose()

Write-Host "Iniciando backend Nostalgia..." -ForegroundColor Cyan

# Ejecutar Spring Boot
.\mvnw.cmd spring-boot:run
