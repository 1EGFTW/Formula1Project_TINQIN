# Formula1Project_TINQIN

Имаме шофьори, които могат да бъдат главни или резервни. Всички тe карат 1 кола и имат 1 отбор. 
Шофьорите могат да сменят отбори, да печелят точки от всяко състезание и титли.
При наличие на достатъчно точки, събрани през сезона, при пресмятане чрез алгоритъм, може да се изчисли кой шофьор ще спечели титлата за сезона.
Има функционалност за справка за най-успешен шофьор с най-много на брой титли.
Всеки отбор има набор от шофьори, който трябва да са основни (primary), както и инжинерен екип.
Отборът може да назначава и уволнява инжинери. Всеки инжинер от всеки отбор е назначен на определена позиция.
Всеки отбор събира точки от всяко състезание, като отбора с най-много точки печели отборна купа.
Има функционалност за справка кой е най-успешния отбор, като има 2 възможности - успехи в историята (по брой отборни титли) или в реално време (по брой титли на шофьорите в отбора)
Имаме състезания, които са част от сезон, имат координати и дата на провеждане. 
Според датата на провеждане на състезанието се генерира карта с точното местонахождение на пистата.
Спрямо координатите на пистата, се генерира прогноза за времето в реално време.
Всяка писта има дължина, както и брой обиколки на състезание. 
Имаме възможност за справка коя е най-дългата писта сред всички налични в БД.
Всички състезания за част от 1 сезон, който има един победител (шофьор)

Системата поддържа стандартни операции за добавяне/обновяване/търсене/изтриване/взимате на данни за:
Шофьори
Коли
Отбори
Инжинери
Вид инжинери
Състезания
Сезони
