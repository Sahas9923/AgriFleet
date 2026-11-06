@echo off
cd /d "%~dp0"

for %%d in (agrifleet-backend-core-service agrifleet-backend-task1 agrifleet-backend-task2 agrifleet-backend-task3 agrifleet-backend-task4 agrifleet-backend-task5) do (
  echo === %%d ===
  if not exist "%%d\Dockerfile" copy /y agrifleet-backend-core-service\Dockerfile "%%d\Dockerfile" >nul
  copy /y AgriFleet.db "%%d\AgriFleet.db" >nul
  powershell -NoProfile -Command "$f='%%d\Dockerfile'; $t=Get-Content $f -Raw; $t=$t -replace '/data`nCOPY', ('/data'+[char]10+'COPY'); $t=$t -replace '-DskipTests','-Dmaven.test.skip=true'; if ($t -notmatch 'COPY AgriFleet.db') { $t=$t -replace 'RUN mkdir -p /data', ('RUN mkdir -p /data'+[char]10+'COPY AgriFleet.db /data/AgriFleet.db') }; Set-Content -Path $f -Value $t -NoNewline"
  git add -f "%%d/AgriFleet.db" "%%d/Dockerfile"
)

echo.
echo === Check: should list 6 lines ===
findstr /s /n /c:"COPY AgriFleet.db" Dockerfile

git commit -m "Add AgriFleet.db to all backend services"
git push
pause