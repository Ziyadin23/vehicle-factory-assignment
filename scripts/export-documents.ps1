param()
$ErrorActionPreference = 'Stop'
$root = Split-Path $PSScriptRoot
$docs = Join-Path $root 'docs'
$utf8 = New-Object System.Text.UTF8Encoding($false)
Add-Type -AssemblyName System.Drawing

function Inline([string]$value) {
    $value = [System.Net.WebUtility]::HtmlEncode($value)
    $value = [regex]::Replace($value, '\*\*(.+?)\*\*', '<b>$1</b>')
    $value = [regex]::Replace($value, '\x60([^\x60]+)\x60', '<code>$1</code>')
    $value = [regex]::Replace($value, '(https://github\.com/[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+)', '<a href="$1">$1</a>')
    return $value
}

foreach ($name in @('REPORT', 'PREPARATION_GUIDE')) {
    $html = New-Object System.Text.StringBuilder
    [void]$html.AppendLine('<!doctype html><html><head><meta charset="utf-8"><style>body{font-family:Calibri,Arial,sans-serif;font-size:11pt;color:#182635}h1{font-size:22pt;color:#17365d}h2{font-size:15pt;color:#17365d;margin-top:20pt}h3{font-size:12pt;color:#17365d}p{line-height:1.15;margin:7pt 0}pre{font-family:Consolas,monospace;font-size:9pt;background:#f1f4f8;white-space:pre-wrap}code{font-family:Consolas,monospace}img{max-width:620px}h1,h2,h3{page-break-after:avoid}</style></head><body>')
    $inCode = $false
    foreach ($line in [IO.File]::ReadAllLines((Join-Path $docs ($name + '.md')))) {
        if ($line.StartsWith(([string][char]96) * 3)) {
            if ($inCode) { [void]$html.AppendLine('</pre>') } else { [void]$html.AppendLine('<pre>') }
            $inCode = -not $inCode
        } elseif ($inCode) {
            [void]$html.AppendLine([System.Net.WebUtility]::HtmlEncode($line))
        } elseif ($line -match '^!\[(.*?)\]\((.*?)\)$') {
            $alt = $Matches[1]
            $image = $Matches[2]
            $sourceImage = [Drawing.Image]::FromFile((Join-Path $docs $image))
            $imageWidth = [Math]::Min(620, $sourceImage.Width)
            $imageHeight = [int]($sourceImage.Height * $imageWidth / $sourceImage.Width)
            $sourceImage.Dispose()
            [void]$html.AppendLine(('<p><img src="{0}" alt="{1}" width="{2}" height="{3}"></p>' -f $image,$alt,$imageWidth,$imageHeight))
        } elseif ($line -match '^(#{1,3}) (.*)$') {
            $level = $Matches[1].Length
            [void]$html.AppendLine(('<h{0}>{1}</h{0}>' -f $level,(Inline $Matches[2])))
        } elseif ($line.Trim()) {
            [void]$html.AppendLine(('<p>{0}</p>' -f (Inline $line)))
        }
    }
    [void]$html.AppendLine('</body></html>')
    $htmlText = [regex]::Replace($html.ToString(), '[^\x00-\x7F]', {
        param($match)
        return '&#' + [int][char]$match.Value + ';'
    })
    [IO.File]::WriteAllText((Join-Path $docs ($name + '.html')), $htmlText, $utf8)
}

$word = New-Object -ComObject Word.Application
$word.Visible = $false
$word.DisplayAlerts = 0
try {
    foreach ($name in @('REPORT', 'PREPARATION_GUIDE')) {
        $document = $word.Documents.Open((Join-Path $docs ($name + '.html')), $false, $true)
        try {
            $document.PageSetup.PaperSize = 7
            $document.PageSetup.TopMargin = 42.5
            $document.PageSetup.BottomMargin = 42.5
            $document.PageSetup.LeftMargin = 42.5
            $document.PageSetup.RightMargin = 42.5
            $document.Content.ParagraphFormat.KeepTogether = -1
            $pictureCount = $document.InlineShapes.Count
            for ($pictureIndex = 1; $pictureIndex -le $pictureCount; $pictureIndex++) {
                $picture = $document.InlineShapes.Item($pictureIndex)
                $picture.LockAspectRatio = -1
                if ($picture.Width -gt 510) { $picture.Width = 510 }
                $picture.Range.ParagraphFormat.Alignment = 1
                if ($picture.Type -eq 4) {
                    $picture.LinkFormat.SavePictureWithDocument = $true
                    $picture.LinkFormat.BreakLink()
                }
            }
            Write-Output ($name + ': layout prepared')
            $footer = $document.Sections.Item(1).Footers.Item(1).Range
            $footer.ParagraphFormat.Alignment = 2
            [void]$footer.Fields.Add($footer, 33)
            Write-Output ($name + ': saving documents')
            [string]$docxPath = Join-Path $docs ($name + '.docx')
            $docxFormat = 16
            $document.SaveAs([ref]$docxPath, [ref]$docxFormat)
            Write-Output ($name + ': DOCX saved')
            [string]$pdfPath = Join-Path $docs ($name + '.pdf')
            $document.ExportAsFixedFormat($pdfPath, 17)
            Write-Output ($name + ': ' + $document.ComputeStatistics(2) + ' pages, ' + $document.InlineShapes.Count + ' diagrams')
        } finally {
            $document.Close(0)
            [void][Runtime.InteropServices.Marshal]::ReleaseComObject($document)
        }
    }
} finally {
    $word.Quit()
    [void][Runtime.InteropServices.Marshal]::ReleaseComObject($word)
}
