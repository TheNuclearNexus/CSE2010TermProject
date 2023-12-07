cp -r ./src/ ./out

cd ./out
if javac -d . EvalBogglePlayer.java; then
    java EvalBogglePlayer ../input/words.txt 
fi