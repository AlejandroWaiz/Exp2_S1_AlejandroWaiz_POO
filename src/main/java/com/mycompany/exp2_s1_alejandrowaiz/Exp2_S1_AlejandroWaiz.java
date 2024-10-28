package com.mycompany.exp2_s1_alejandrowaiz;

 import java.util.Scanner;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 /**
  *
  * @author Aleja
  */
 
 public class Exp2_S1_AlejandroWaiz {
 
     public static void main(String[] args) {
 
         Scanner scanner = new Scanner(System.in);
 
         System.out.println("Bienvenido a Bank Boston");
         System.out.println("Para usar nuestra aplicación primero debe crearse una cuenta\n");
 
         Cliente Cliente = null;
 
         while (true) {
             System.out.println("\nMenú:");
             System.out.println("1 - Registrarse como cliente.");
             System.out.println("2 - Ver tus datos.");
             System.out.println("3 - Depositar.");
             System.out.println("4 - Girar.");
             System.out.println("5 - Consultar saldo.");
             System.out.println("6 - Salir.");
             System.out.print("\nSelecciona una opción:\n ");
             int opcion = scanner.nextInt();
             scanner.nextLine();  // Consumir el salto de línea
 
             switch (opcion) {
                 case 1:
 
                     if (Cliente == null){
                         Cliente = CrearCliente(scanner);
                         break;
                     } else {
                         System.out.println("Ya tienes un cliente creado.\n");
                         break;
                     }
                    
                 case 2:
                     if (Cliente == null){
                         System.out.println("Aun no tienes un cliente creado\n");
                         break;
                     } else {
                         Cliente.LoggearInformacion();
                         break;
                     }
 
                 case 3:
                     if (Cliente == null){
                         System.out.println("Aun no tienes un cliente creado\n");
                         break;
                     } else {                   
                         Cliente.getCuenta().Depositar(scanner);
                         break;
                     }
                    
                 case 4:
                     if (Cliente == null){
                         System.out.println("Aun no tienes un cliente creado\n");
                         break;
                     } else {
                        Cliente.getCuenta().Girar(scanner);
                        break;
                     }
                     
                 case 5:
                     if (Cliente == null){
                         System.out.println("Aún no tienes un cliente creado\n");
                         break;
                     } else {
                         System.out.println("Tu saldo actual es de $" + Cliente.getCuenta().getSaldo());
                         break;
                     }
 
                 case 6:
                     if (Cliente == null){
                         System.out.println("Que tengas un buen dia!");
                         return;
                     } else {
                         System.out.println("Tu cuenta cierra con un saldo de $" + Cliente.getCuenta().getSaldo() + ". Que tengas un buen dia!" );
                         return;
                     }
                 
                 default:
                     System.out.println("Elige una opción válida");
             }
 
         }
 
 
     }
 
     private static Cliente CrearCliente(Scanner scanner){
         
         //Validaciones
         String REGEX_RUT = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9kK]$";
         String REGEX_NumeroCuenta = "^[0-9]+$";
         String REGEX_Telefono = "^[0-9]{8}$";
         boolean ClienteValido = false;
 
         //DatosCliente
         Cliente Cliente = null;
         
         String Domicilio;
         String Rut;
         String Telefono;
         String Nombre;
         String NumeroCuenta;
         int TipoCuenta;
 
         do { 
 
         System.out.println("Ingrese su nombre...");
         Nombre = scanner.next();
 
         System.out.println("Ingrese su rut (con puntos y guión)...");
         Rut = scanner.next();
 
         Pattern Rutpattern = Pattern.compile(REGEX_RUT);
         Matcher Rutmatcher = Rutpattern.matcher(Rut);
 
         if (!Rutmatcher.matches()){
             System.out.println("Rut inválido, vuelve a intentarlo");    
             continue;       
         }
 
         System.out.println("Ingrese su domicilio...");
         Domicilio = scanner.next();
 
         System.out.println("Ingrese su telefono (sin el +569)...");
         Telefono = scanner.next();
 
         Pattern TelefonoPattern = Pattern.compile(REGEX_Telefono);
         Matcher TelefonoMatcher = TelefonoPattern.matcher(Telefono);
 
         if (!TelefonoMatcher.matches()){
             System.out.println("Número invalido, intentalo de nuevo");
             continue;
         }
 
         System.out.println("Ingrese su numero de cuenta deseado (solo numeros y hasta 9 de ellos)...");
         NumeroCuenta = scanner.next();
 
         Pattern NumeroCuentaPattern = Pattern.compile(REGEX_NumeroCuenta);
         Matcher NumeroCuentaPatternMatcher = NumeroCuentaPattern.matcher(NumeroCuenta);
 
         if (!NumeroCuentaPatternMatcher.matches()){
             System.out.println("La cuenta solo puede poseer números, intentalo de nuevo");
             continue;
         } else if (NumeroCuenta.length() > 9) {
             System.out.println("Esos son mas de 9 números, vuelve a intentarlo");    
             continue;   
         }

         System.out.println("¿Que tipo de cuenta desea crear?");
         System.out.println("1.- Cuenta Corriente");
         System.out.println("2.- Cuenta Ahorro");
         System.out.println("3.- Cuenta Credito");
         TipoCuenta = scanner.nextInt();

         if (TipoCuenta != 1 && TipoCuenta != 2 && TipoCuenta != 3){
            System.out.println("Opción inválida. Por favor intentelo nuevamente");
            continue;
         } else {
            switch (TipoCuenta) {
                case 1:
                    CuentaCorriente CuentaCorriente = new CuentaCorriente(NumeroCuenta);
                    Cliente = new Cliente(Rut, Nombre, Domicilio, "+569" + Telefono, CuentaCorriente);
                    break;

                case 2:
                    CuentaAhorro CuentaAhorro = new CuentaAhorro(NumeroCuenta);
                    Cliente = new Cliente(Rut, Nombre, Domicilio, "+569" + Telefono, CuentaAhorro);
                    break;

                case 3:
                    CuentaCredito CuentaCredito = new CuentaCredito(NumeroCuenta);
                    Cliente = new Cliente(Rut, Nombre, Domicilio, "+569" + Telefono, CuentaCredito);
                    break;
            }
         }
 
         ClienteValido = true;
 
         } while (ClienteValido == false);

         System.out.println(Cliente.getCuenta().getTipoCuenta() + " creada exitosamente!\n");
 
         return Cliente;
         
     }
     
 }

 class Cliente implements Logger {
 
    private String Rut;
    private String Nombre;
    private String Domicilio;
    private String Telefono;
    private Cuenta Cuenta;

    public Cliente (String Rut, String Nombre, String Domicilio, String Telefono, Cuenta Cuenta) {
        this.Rut = Rut;
        this.Nombre = Nombre;
        this.Domicilio = Domicilio;
        this.Telefono = Telefono;
        this.Cuenta = Cuenta;
    }

    @Override
    public void LoggearInformacion(){
        System.out.println("\nNombre: " + Nombre);
        System.out.println("Rut: " + Rut);
        System.out.println("Domicilio: " + Domicilio);
        System.out.println("Telefono: " + Telefono);
        System.out.println("Tipo de cuenta: " + Cuenta.getTipoCuenta());
        System.out.println("Saldo:$" + Cuenta.getSaldo());
    }

    public Cuenta getCuenta(){
        return this.Cuenta;
    }

 }

 abstract class Cuenta {

    private String NumeroCuenta; 
    private long Saldo;

    public Cuenta(String NumeroCuenta){
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = 0;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    
    public long getSaldo() {
        return Saldo;
    }

    public abstract String getTipoCuenta();

    public abstract void Depositar(Scanner scanner);

    public abstract void Girar(Scanner scanner);

 }

 class CuentaCorriente extends Cuenta {

    private String NumeroCuenta; 
    private long Saldo;

    public CuentaCorriente(String NumeroCuenta){
        super(NumeroCuenta);
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = 0;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Corriente";
    }

    public long getSaldo() {
        return Saldo;
    }

    public void setSaldo(long Saldo){
        this.Saldo = Saldo;
    }

    @Override
    public void Depositar(Scanner scanner){

        boolean depositoValido = false;
         long deposito = 0;
     
         while (!depositoValido) {
             System.out.println("¿Cuánto dinero deseas depositar?");
     
             if (scanner.hasNextLong()) {
                 deposito = scanner.nextLong();
                 if (deposito <= 0) {
                     System.out.println("Depósito inválido, debe ser mayor a 0");
                 } else {
                     this.Saldo = this.Saldo + deposito;
                     System.out.println("$" + deposito + " depositados exitosamente!");
                     depositoValido = true;
                 }
             } else {
                 System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                 scanner.next(); 
             }
             scanner.nextLine(); 
         }

    }
    
    @Override
    public void Girar(Scanner scanner){

        boolean giroValido = false;
        long giro = 0;
    
        while (!giroValido) {
            System.out.println("¿Cuánto dinero deseas girar?");
    
            if (scanner.hasNextLong()) {
                giro = scanner.nextLong();
                if (giro <= 0) {
                    System.out.println("Giro inválido, debe ser mayor a 0");
                } else if (this.Saldo < giro) {
                    System.out.println("Giro inválido, estás intentando girar $" + giro + " y solo tienes $" + this.Saldo + " en la cuenta.");
                } else {
                    this.Saldo = this.Saldo - giro;
                    System.out.println("$" + giro + " girados exitosamente!");
                    giroValido = true;
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                scanner.next();
            }
            scanner.nextLine(); 
        }
      }
    }

 class CuentaAhorro extends Cuenta {

    private String NumeroCuenta; 
    private long Saldo;

    public CuentaAhorro(String NumeroCuenta){
        super(NumeroCuenta);
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = 0;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Ahorro";
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public long getSaldo() {
        return Saldo;
    }

    public void setSaldo(long Saldo){
        this.Saldo = Saldo;
    }

    @Override
    public void Depositar(Scanner scanner){

        boolean depositoValido = false;
         long deposito = 0;
     
         while (!depositoValido) {
             System.out.println("¿Cuánto dinero deseas depositar?");
     
             if (scanner.hasNextLong()) {
                 deposito = scanner.nextLong();
                 if (deposito <= 0) {
                     System.out.println("Depósito inválido, debe ser mayor a 0");
                 } else {
                     this.Saldo = this.Saldo + deposito;
                     System.out.println("$" + deposito + " depositados exitosamente!");
                     depositoValido = true;
                 }
             } else {
                 System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                 scanner.next(); 
             }
             scanner.nextLine(); 
         }

    }
    
    @Override
    public void Girar(Scanner scanner){

        boolean giroValido = false;
        long giro = 0;
    
        while (!giroValido) {
            System.out.println("¿Cuánto dinero deseas girar?");
    
            if (scanner.hasNextLong()) {
                giro = scanner.nextLong();
                if (giro <= 0) {
                    System.out.println("Giro inválido, debe ser mayor a 0");
                } else if (this.Saldo < giro) {
                    System.out.println("Giro inválido, estás intentando girar $" + giro + " y solo tienes $" + this.Saldo + " en la cuenta.");
                } else {
                    this.Saldo = this.Saldo - giro;
                    System.out.println("$" + giro + " girados exitosamente!");
                    giroValido = true;
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                scanner.next();
            }
            scanner.nextLine(); 
        }
      }

 }

 

 class CuentaCredito extends Cuenta {

    private String NumeroCuenta; 
    private long Saldo;

    public CuentaCredito(String NumeroCuenta){
        super(NumeroCuenta);
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = 0;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta de Credito";
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public long getSaldo() {
        return Saldo;
    }

    public void setSaldo(long Saldo){
        this.Saldo = Saldo;
    }

    @Override
    public void Depositar(Scanner scanner){

        boolean depositoValido = false;
         long deposito = 0;
     
         while (!depositoValido) {
             System.out.println("¿Cuánto dinero deseas depositar?");
     
             if (scanner.hasNextLong()) {
                 deposito = scanner.nextLong();
                 if (deposito <= 0) {
                     System.out.println("Depósito inválido, debe ser mayor a 0");
                 } else {
                     this.Saldo = this.Saldo + deposito;
                     System.out.println("$" + deposito + " depositados exitosamente!");
                     depositoValido = true;
                 }
             } else {
                 System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                 scanner.next(); 
             }
             scanner.nextLine(); 
         }

    }
    
    @Override
    public void Girar(Scanner scanner){

        boolean giroValido = false;
        long giro = 0;
    
        while (!giroValido) {
            System.out.println("¿Cuánto dinero deseas girar?");
    
            if (scanner.hasNextLong()) {
                giro = scanner.nextLong();
                if (giro <= 0) {
                    System.out.println("Giro inválido, debe ser mayor a 0");
                } else if (this.Saldo < giro) {
                    System.out.println("Giro inválido, estás intentando girar $" + giro + " y solo tienes $" + this.Saldo + " en la cuenta.");
                } else {
                    this.Saldo = this.Saldo - giro;
                    System.out.println("$" + giro + " girados exitosamente!");
                    giroValido = true;
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese solo números.");
                scanner.next();
            }
            scanner.nextLine(); 
        }
      }
 }



 