@ECHO OFF
setlocal
set DEST_PATH=src/assets
set INPUT_PATH=%DEST_PATH%/custom-themes/

echo Building custom theme scss files.

rem Get the files
set FILES=(find src/assets/custom-themes -name "*.scss")

for FILE in %FILES%
do (
  set FILENAME=%FILE%#%INPUT_PATH%
  set BASENAME=%FILENAME%.scss
  (npm bin)/node-sass %FILE% > %DEST_PATH%/%BASENAME%.css
)

echo Finished building CSS.
