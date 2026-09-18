# RideReady — Factory Method & Abstract Factory

Java 17 console application for Assignment 2, ShP-2216, Astana IT University.
Topic: vehicle rentals and compatible accessory families (Option B).

Repository: https://github.com/Ziyadin23/vehicle-factory-assignment

## Submission and study files
- [Report (PDF)](docs/REPORT.pdf)
- [Defense preparation guide (PDF)](docs/PREPARATION_GUIDE.pdf)
- [Requirements checklist](docs/REQUIREMENTS_CHECKLIST.md)
- [Factory Method UML](docs/factory-method.png) and [Abstract Factory UML](docs/abstract-factory.png)
- [Demo output](docs/demo-output.txt) and [test results](docs/test-output.txt)

Student: Ziyadinkhan Kudaibergenuly, SE2501. This repository is private: grant your
instructor access or change visibility according to course instructions. Submit the
report and repository link to Moodle separately.

## Run
Install JDK 17 or newer, then from this folder:
```powershell
powershell -ExecutionPolicy Bypass -File .\run.ps1
powershell -ExecutionPolicy Bypass -File .\run.ps1 -Mode test
```
The Windows script also detects the portable JDK in the adjacent VehicleFactoryTools folder.
On macOS/Linux, with Java on PATH: `sh run.sh` or `sh run.sh test`.
In IntelliJ: open this folder, select JDK 17, mark src/main/java as Sources Root and src/test/java as Test Sources Root, then run Main or PatternTests.

## Structure
- src/main/java/rideready/factorymethod: Vehicle products and VehicleFactory creators.
- src/main/java/rideready/abstractfactory: SafetyEquipment + SecurityDevice families.
- src/main/java/rideready/client: interface-based AccessoryRental client.
- src/test/java/rideready: dependency-free behavioral tests.
- docs: report, UML sources and rendered diagrams, defense guide and requirement checklist.

## Design
MotorcycleFactory and CarFactory override createVehicle(). The inherited prepareRental()
uses the Vehicle interface. MotorcycleAccessoryFactory supplies Helmet + DiscLock;
CarAccessoryFactory supplies ChildSeat + CarAlarm. AccessoryRental receives only an
AccessoryFactory and never constructs concrete products. Main is the composition root:
it selects and connects implementations explicitly, with no type-selection switch.

The application is a teaching simulation, not a real rental, pricing, or safety system.
Review and adapt the work according to your course's individual-work and AI-use rules.

## Regenerating UML diagrams
UML sources are editable PlantUML files; render them with PlantUML's Smetana layout
(`java -jar plantuml.jar -Playout=smetana -tpng docs/*.puml`).
