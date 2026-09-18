# Assignment traceability
| Requirement | Evidence |
|---|---|
| Java 17, complete code | src/main/java; verified with javac --release 17; VS Code run instructions in README |
| Product and two implementations | Vehicle, Motorcycle, Car |
| Creator and two concrete creators | VehicleFactory, MotorcycleFactory, CarFactory |
| Two abstract product types | SafetyEquipment, SecurityDevice |
| Two complete product families | Helmet + DiscLock; ChildSeat + CarAlarm |
| Abstract factory and concrete factories | AccessoryFactory; MotorcycleAccessoryFactory; CarAccessoryFactory |
| Interface-only Abstract Factory client | client/AccessoryRental.java |
| No product-selection switch in client | polymorphic workflow and injected factory |
| At least five Clean Code principles with excerpts | Report Section 4 (six principles) |
| Both UML diagrams | docs/*-factory.puml, factory-method.puml and corresponding PNGs |
| Report introduction and conclusion | Report Sections 1 and 7 |
| GitHub URL | https://github.com/Ziyadin23/vehicle-factory-assignment (private; grant instructor access) |
| Incremental history | Separate commits for scaffold, Part A, Part B, tests and documentation |
| Demo and instructor preparation | PREPARATION_GUIDE.pdf and recorded console output |
| Personal details | Ziyadinkhan Kudaibergenuly, SE2501 |
| Moodle submission | Student action; GitHub upload is separate |
