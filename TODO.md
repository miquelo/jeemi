* Todos los proveedores de maquinaria soportan XA con el nivel de transacción
  "serializable". En caso de "docker":
  - Entre "begin" y "end" se ejecutan las operaciones físicas con el atributo
    en servidor que indica que están en "creating" y su XID
  - En "end" se cambia el atriubuto en servidor que indica que están en
    "created" y su XID
  - En "prepare" se cambia el atriubutos en servidor que indica que están en
    "prepared" y su XID
  - En "commit" se cambia el atriubutos en servidor que indica que están en
    "commited" SIN su XID
  - El "rollback" elimina (no se puede restaurar) todo elemento con el
    atributo de servidor "created" y "prepared"

