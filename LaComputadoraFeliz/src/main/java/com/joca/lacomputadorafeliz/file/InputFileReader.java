/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.file;

import com.joca.lacomputadorafeliz.computers.AdminAssignments;
import com.joca.lacomputadorafeliz.database.DBAssembly;
import com.joca.lacomputadorafeliz.database.DBComponents;
import com.joca.lacomputadorafeliz.database.DBComputers;
import com.joca.lacomputadorafeliz.database.DBUsers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.exceptions.NotEnoughComponentsException;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.computers.Component;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
import com.joca.lacomputadorafeliz.model.computers.Computer;
import com.joca.lacomputadorafeliz.model.file.FileInstructionEnum;
import com.joca.lacomputadorafeliz.model.users.PasswordVTO;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author joca
 */
public class InputFileReader {

    private HttpSession session;

    public InputFileReader(HttpSession session) {
        this.session = session;
    }

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(\\w+)\\((.*?)\\)$");

    public List<String> readFile(Part filePart) throws IOException {
        List<String> errors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<String> possibleError = processLine(line);
                if (possibleError.isPresent()) {
                    errors.add("ERROR: " + line + "\n--- " + possibleError.get());
                }
            }
        }
        return errors;
    }

    private Optional<String> processLine(String line) {
        Matcher matcher = INSTRUCTION_PATTERN.matcher(line.trim());
        Optional<String> posibleError;
        if (matcher.matches()) {
            String instruction = matcher.group(1);
            String params = matcher.group(2);
            List<String> paramList = Arrays.asList(params.split("\\s*,\\s*")); // Divide por comas
            if (instruction.equals(FileInstructionEnum.COMPUTADORA.name())) {
                posibleError = processComputadora(paramList);
            } else if (instruction.equals(FileInstructionEnum.PIEZA.name())) {
                posibleError = processPieza(paramList);
            } else if (instruction.equals(FileInstructionEnum.USUARIO.name())) {
                posibleError = processUsuario(paramList);
            } else if (instruction.equals(FileInstructionEnum.ENSAMBLAR_COMPUTADORA.name())) {
                posibleError = processEnsamblarComputadora(paramList);
            } else if (instruction.equals(FileInstructionEnum.ENSAMBLE_PIEZAS.name())) {
                posibleError = processEnsamblePiezas(paramList);
            } else {
                posibleError = Optional.of("Error de formato: " + instruction + " no es una instruccion valida");
            }
        } else {
            return Optional.of("Error de formato: " + line + " no esta en un formato valido");
        }
        return posibleError;
    }

    private Optional<String> processUsuario(List<String> params) {
        Optional<String> posibleError = validateParameters(params, 3);
        if (posibleError.isPresent()) {
            return posibleError;
        }

        User user = new User();
        if (!isStringValid(params.get(0))) {
            return Optional.of("Error de formato: " + params.get(0) + " no es un nombre valido");
        }
        String computerName = removeQuotes(params.get(0));
        user.setName(computerName);
        user.setUserName(computerName);

        String passwordUnencripted = params.get(1);
        if (!isStringValid(passwordUnencripted)) {
            return Optional.of("Error de formato: " + passwordUnencripted + " no esta en un formato valido");
        }
        passwordUnencripted = removeQuotes(passwordUnencripted);
        if (passwordUnencripted.length() < 6) {
            return Optional.of("Error de formato: " + passwordUnencripted + " debe ser mayor o igual a 6 caracteres");
        }
        PasswordVTO password = new PasswordVTO(passwordUnencripted, true);

        int idRol;
        try {
            idRol = Integer.parseInt(params.get(2));
        } catch (NumberFormatException e) {
            return Optional.of("Error de formato: " + params.get(2) + " no es un codigo valido");
        }
        if (idRol <= 0 || idRol > 3) {
            return Optional.of("Error logico: " + idRol + " no es un rol valido en el sistema");
        }
        UserRol userRol = new UserRol();
        userRol.setId(idRol);
        user.setUserRol(userRol);

        try {
            DBUsers dBUsers = new DBUsers(session);
            dBUsers.createUserWithPassword(user, password);
        } catch (ClassNotFoundException | SQLException e) {
            return Optional.of("Error lógico: No se pudo crear el usuario");
        } catch (InvalidDataException e) {
            return Optional.of("Error lógico: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<String> processPieza(List<String> params) {
        Optional<String> posibleError = validateParameters(params, 2);
        if (posibleError.isPresent()) {
            return posibleError;
        }

        Component component = new Component();
        if (!isStringValid(params.get(0))) {
            return Optional.of("Error de formato: " + params.get(0) + " no es un nombre valido");
        }
        String componentName = removeQuotes(params.get(0));
        component.setName(componentName);

        double value;
        try {
            value = Double.parseDouble(params.get(1));
        } catch (NumberFormatException e) {
            return Optional.of("Error de formato: " + params.get(1) + " no es un valor valido");
        }
        if (value <= 0) {
            return Optional.of("Error logico: " + value + " no puede ser menor o igual a 0");
        }
        component.setValue(value);
        component.setAmount(100);
        try {
            DBComponents dBComponents = new DBComponents(session);
            dBComponents.create(component);
        } catch (ClassNotFoundException | SQLException e) {
            return Optional.of("Error lógico: No se pudo crear el componente");
        } catch (InvalidDataException e) {
            return Optional.of("Error lógico: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<String> processComputadora(List<String> params) {
        Optional<String> posibleError = validateParameters(params, 2);
        if (posibleError.isPresent()) {
            return posibleError;
        }

        Computer computer = new Computer();
        if (!isStringValid(params.get(0))) {
            return Optional.of("Error de formato: " + params.get(0) + " no es un nombre valido");
        }
        String componentName = removeQuotes(params.get(0));
        computer.setName(componentName);

        double price;
        try {
            price = Double.parseDouble(params.get(1));
        } catch (NumberFormatException e) {
            return Optional.of("Error de formato: " + params.get(1) + " no es un precio valido");
        }
        if (price <= 0) {
            return Optional.of("Error logico: " + price + " no puede ser menor o igual a 0");
        }
        computer.setPrice(price);
        try {
            DBComputers dBComputers = new DBComputers(session);
            dBComputers.createComputer(computer);
        } catch (ClassNotFoundException | SQLException e) {
            return Optional.of("Error lógico: No se pudo crear la computadora");
        } catch (InvalidDataException e) {
            return Optional.of("Error lógico: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<String> processEnsamblePiezas(List<String> params) {
        Optional<String> posibleError = validateParameters(params, 3);
        if (posibleError.isPresent()) {
            return posibleError;
        }

        ComponentAsignDTO assignment = new ComponentAsignDTO();
        if (!isStringValid(params.get(0))) {
            return Optional.of("Error de formato: " + params.get(0) + " no es un nombre de computadora valido");
        }
        String computerName = removeQuotes(params.get(0));
        assignment.setComputerName(computerName);

        if (!isStringValid(params.get(1))) {
            return Optional.of("Error de formato: " + params.get(1) + " no es un nombre de componente valido");
        }
        String componentName = removeQuotes(params.get(1));
        assignment.setComponentName(componentName);

        int amount;
        try {
            amount = Integer.parseInt(params.get(2));
        } catch (NumberFormatException e) {
            return Optional.of("Error de formato: " + params.get(2) + " no es una cantidad valido");
        }
        if (amount <= 0) {
            return Optional.of("Error logico: " + amount + " debe ser mayor a 0");
        }
        assignment.setAmount(amount);

        try {
            AdminAssignments adminAssignments = new AdminAssignments(session);
            adminAssignments.createAssignment(assignment);
        } catch (ClassNotFoundException | SQLException e) {
            return Optional.of("Error lógico: No se pudo asignar el componente");
        } catch (InvalidDataException | EntityNotFound e) {
            return Optional.of("Error lógico: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<String> processEnsamblarComputadora(List<String> params) {
        Optional<String> posibleError = validateParameters(params, 3);
        if (posibleError.isPresent()) {
            return posibleError;
        }

        AssemblyDTO assembly = new AssemblyDTO();
        if (!isStringValid(params.get(0))) {
            return Optional.of("Error de formato: " + params.get(0) + " no es un nombre de computadora valido");
        }
        String computerName = removeQuotes(params.get(0));
        assembly.setComputerName(computerName);

        String userName = params.get(1);
        if (userName.contains(" ")) {
            return Optional.of("Error de formato: " + params.get(1) + " no es un nombre de usuario valido");
        }
        assembly.setAssemblerUser(userName);

        LocalDate date;
        if (!isStringValid(params.get(2))) {
            return Optional.of("Error de formato: " + params.get(2) + " no esta en un formato valido");
        }
        String dateFixed = removeQuotes(params.get(2));
        try {
            date = LocalDate.parse(dateFixed, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return Optional.of("Error de formato: " + params.get(2) + " no es una fecha valido");
        }
        if (date == null) {
            return Optional.of("Error de formato: la fecha no es valida");
        }
        assembly.setDate(date);

        try {
            DBAssembly dBAssembly = new DBAssembly(session);
            dBAssembly.newAssembly(assembly);
        } catch (ClassNotFoundException | SQLException e) {
            return Optional.of("Error lógico: No se pudo ensamblar la computadora");
        } catch (EntityNotFound | NotEnoughComponentsException e) {
            return Optional.of("Error lógico: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<String> validateParameters(List<String> params, int amount) {
        if (params.size() != amount) {
            return Optional.of("Erro de formato: se esperaban " + amount + " parametros");
        }
        return Optional.empty();
    }

    private boolean isStringValid(String string) {
        return (string.startsWith("\"") && string.endsWith("\"")) ||
                (string.startsWith("“") && string.endsWith("“")) ||
                (string.startsWith("“") && string.endsWith("”")) ||
                (string.startsWith("”") && string.endsWith("“")) ||
                (string.startsWith("”") && string.endsWith("”")) ||
                (string.startsWith("'") && string.endsWith("'"));
    }

    private String removeQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }
}
