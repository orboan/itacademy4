# Repositori amb exercicis resolts d'IT Academy
## BE Java - Mòdul 7 (Herència)
### Nivell 3 (Vehicles)

A tenir en compte:

Els requeriments de l'enunciat de l'exerici es compleixen i, addicionalment tenim:

* En crear un titular, se'l pot assignar **opcionalment**  a un vehicle. Pot ser un vehicle nou a crear o un d'existent. Si n'és un ja d'existent, amb un titular ja assignat, preguntarà si es vol sobreescriure.
* En crear un conductor, cal assignar-lo a un vehicle (aquell que conduirà). Es poden assignar tants conductors com es vulgui a un vehicle mentre les llicències siguin compatibles.
* En assignar un vehicle a un usuari, se'n pot crear un de nou o escollir-ne un de la llista de vehicles ja donats d'alta. Si no hi ha cap vehicle donat d'alta, caldrà crear-ne un de nou, de tipus compatible amb la llicència de l'usuari.
* En crear un nou vehicle, cal assignar-li un titular.
* En assignar un titular a un vehicle, es pot escollir de la llista d'usuaris titulars ja donats d'alta, o crear-ne un de nou. Si no hi ha cap titular donat d'alta, caldrà crear-ne un de nou.

FAQs:
1. Es pot crear un titular sense assignar-lo a un vehicle? Sí, en crear un titular es pot assignar o no, a un vehicle.
2. Es pot assignar un titular a un vehicle un cop creat el titular? En aquesta versió del programa, no, encara no es pot. Per assignar un titular a un vehicle es pot fer en el moment de crear un titular (opcionalment) o en el moment de crear un vehicle (mandatory).
3. Es pot crear un conductor i no assignar-lo a cap vehicle? No, és obligatori assignar cada conductor a un vehicle compatible amb la seva llicència.
4. Es pot fer que un titular sigui també conductor? Sí, durant el procés de creació d'un titular, al final, es dóna l'opció que sigui també, si es vol, conductor.
5. A l'hora d'assignar titulars als vehicles o vehicles als titulars, és obligatori crear un nou titular/vehicle? Només és obligatori si no n'hi ha cap donat d'alta ja prèviament al sistema. En cas que ja n'hi hagi, se'n podrà seleccionar un de la llista (de titulars o de vehicles, segons el cas).

En aquesta solució tot el codi dels menus està a Main, però he afegit comentaris i ordenat el codi de manera que es pugui entendre (amb paciència).


