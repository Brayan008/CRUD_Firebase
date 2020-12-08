package mx.edu.utng.crud_firebase.model;

public class Persona {
    private String pId;
    private String Nombre;
    private String Apellidos;
    private String Correo;
    private String LProgramacion;
    private String Password;

    public Persona() {
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getLProgramacion() {
        return LProgramacion;
    }

    public void setLProgramacion(String LProgramacion) {
        this.LProgramacion = LProgramacion;
    }

    @Override
    public String toString() {
        return Nombre+""+Apellidos+"\n"+""+"Correo: "+Correo+"\n"+"Lengujes: "+LProgramacion;
    }
}
