# RideReady — Factory Method & Abstract Factory

Java 17 console application for Assignment 2, ShP-2216, Astana IT University.
Topic: vehicle rentals and compatible accessory families (Option B).

Repository: https://github.com/Ziyadin23/vehicle-factory-assignment

## Submission and study files
- [Report (PDF)](docs/REPORT.pdf)
- [Defense preparation guide (PDF)](docs/PREPARATION_GUIDE.pdf)
- [Design patterns concepts and foundations (PDF)](docs/DESIGN_PATTERNS_BASICS.pdf)
- [Requirements checklist](docs/REQUIREMENTS_CHECKLIST.md)
- [Factory Method UML](docs/factory-method.png) and [Abstract Factory UML](docs/abstract-factory.png)
- [Demo output](docs/demo-output.txt) and [test results](docs/test-output.txt)

Student: Ziyadinkhan Kudaibergenuly, SE2501. This repository is private: grant your
instructor access or change visibility according to course instructions. Submit the
report and repository link to Moodle separately.

## Run in VS Code
1. Open the entire project folder in VS Code with the Extension Pack for Java installed.
2. Use **Java: Configure Java Runtime** from the Command Palette to select JDK 17 for this project.
3. Open `src/main/java/rideready/Main.java` and click **Run** above `main()` to run the demo.
4. Open `src/test/java/rideready/PatternTests.java` and click **Run** above `main()` to run the 16 behavioral checks.

The included VS Code settings identify both Java source folders. PatternTests is a standalone
Java program, so run its main method directly. No build scripts or external Java libraries are needed.
See the [official VS Code Java guide](https://code.visualstudio.com/docs/java/java-project)
for runtime configuration.

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
