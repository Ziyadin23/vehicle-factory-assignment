param([ValidateSet('demo','test')][string]$Mode = 'demo')
$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$javaBin = $null
if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME 'bin\javac.exe'))) {
    $javaBin = Join-Path $env:JAVA_HOME 'bin'
} elseif (Get-Command javac -ErrorAction SilentlyContinue) {
    $javaBin = Split-Path (Get-Command javac).Source
} else {
    $portable = Join-Path (Split-Path $root) 'VehicleFactoryTools\jdk'
    $compiler = Get-ChildItem $portable -Filter javac.exe -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($compiler) { $javaBin = $compiler.DirectoryName }
}
if (-not $javaBin) { throw 'JDK 17 required. Set JAVA_HOME or add its bin folder to PATH.' }
$build = Join-Path $root 'build'
New-Item -ItemType Directory -Path $build -Force | Out-Null
$sources = @(Get-ChildItem (Join-Path $root 'src') -Filter *.java -Recurse | ForEach-Object FullName)
& (Join-Path $javaBin 'javac.exe') --release 17 -encoding UTF-8 -Xlint:all -Werror -d $build $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
$mainClass = if ($Mode -eq 'test') { 'rideready.PatternTests' } else { 'rideready.Main' }
& (Join-Path $javaBin 'java.exe') -cp $build $mainClass
if ($LASTEXITCODE -ne 0) { throw 'Execution failed.' }
