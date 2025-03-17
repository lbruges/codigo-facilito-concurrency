# codigo-facilito-concurrency
## Environment Configuration
JDK: 1.21

## Application Properties
All properties are optional and have a default value if not defined

### Sample Global properties:
Create a new file named <b>application.properties</b> and add it to the out/production folder (if running from IDE), or to the same path that contains the compiled jar.<br> 
Define the following properties:
```
backtracker.printer.enabled=true
backtracker.printer.output=FILE
backtracker.printer.filename=result.txt
matrix.printer.enabled=true
matrix.printer.output=FILE
matrix.printer.filename=matrix.txt
matrix.concurrency.enabled=true
# matrix.concurrency.pool-size=2
matrix.concurrency.seq-threshold=20
matrix.score.gap=-2
matrix.score.match=1
matrix.score.miss=-1
matrix.log-exec-time=true
```
<b>Note:</b> please keep in mind that  matrix.concurrency.pool-size defaults to the number of available processors

### Sample Web Request properties:
Create a new file named <b>request.properties</b> and add it to the out/production folder (if running from IDE), or to the same path that contains the compiled jar. <br>
Define the following properties:
```
req.url=https://rest.ensembl.org/sequence/id/%s?content-type=application/json
req.seq-a-id=ENSG00000239615
req.seq-b-id=ENSG00000239617
```