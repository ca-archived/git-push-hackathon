
echo "search current directory"

$current_dir = Convert-Path .
echo "current_dir is "
echo $current_dir

echo "Set-Location to current directory"
Set-Location $current_dir

echo "EXCUTE ng serve --open"
ng serve --open

Start-Sleep -s 20