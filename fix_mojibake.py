from pathlib import Path

path = Path('index.html')
text = path.read_text(encoding='utf-8', errors='replace')
try:
    fixed = text.encode('latin1').decode('utf-8')
except Exception:
    fixed = text
if fixed != text:
    path.write_text(fixed, encoding='utf-8')
    print('fixed')
else:
    print('nochange')
