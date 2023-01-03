/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ws.sebelumuas;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author hp
 */
@Controller
@ResponseBody
public class BarangController {
     Barang data = new Barang();
     BarangJpaController actrl = new BarangJpaController();
     
     @GetMapping("/getNama/{id}")
    public String getNama(@PathVariable("id") int id) {
        
        data = actrl.findBarang(id);
        return data.getNama();
        
    }
   @GetMapping("/getall")
    public List<Barang> viewAll(){
        
        return actrl.findBarangEntities();        
    }

    @PostMapping("/postBarang")
    public String createBarang(@RequestBody Barang barang) {
        try {
            actrl.create(barang);
            return "Barang berhasil ditambahakan ";
        } catch (Exception e) {
            return " id sudah ada";
        }
    }
    @PutMapping("/putBarang/{id}")
    public String updateBarang(@PathVariable("id") int id, @RequestBody Barang barang) {
        try {
            data = actrl.findBarang(id);
            barang.setId(id);
            Barang brng = new Barang();
            brng = actrl.findBarang(id);
            
            if (barang.getId() != id) {
                return " Barang tidak ada";
            } 
            else if (barang.getNama() == null) {
                barang.setNama(data.getNama());
                actrl.edit(barang);
                brng = actrl.findBarang(id);
                return "--Barang updated--" + "\n\nid :  " + brng.getId() + "\n\nnama : " + brng.getNama() + "\n\njumlah : " + brng.getJumlah();
            } 
            else if (barang.getJumlah() == null) {
                barang.setJumlah(data.getJumlah());
                actrl.edit(barang);
                brng = actrl.findBarang(id);
                return "--Barang updated--"+ "\n\nid :  " + brng.getId() + "\n\nnama : " + brng.getNama() + "\n\njumlah : " + brng.getJumlah();
            } 
            else if (barang.getNama() != null && barang.getJumlah() != null) {
                actrl.edit(barang);
                brng = actrl.findBarang(id);
                return "--Barang updated--" + "\n\nid :  " + brng.getId() + "\n\nnama : " + brng.getNama() + "\n\njumlah : " + brng.getJumlah();
            } else {
                return "Data tidak ditemukan";
            }
        } catch (Exception e) {
            return "Data tidak ada";
        }
    }
    @RequestMapping("/deleteBarang/{id}")
    public String deleteById(@PathVariable("id") int id){
        try{
            actrl.destroy(id);
            return id + " : deleted";
        }
        catch (Exception error){return "error";}
    
    }
    
}
