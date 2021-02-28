
package com.challenge.university.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;


public class RenderPage <T>{
    private String url;
    private Page<T> page;
    private int totalPag;
    private int elementsPag;
    private int pagActual;
    private List<ElementsPage> paginas;
    
    
    public RenderPage(String url, Page<T> page){
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<ElementsPage>();
        
        totalPag = page.getTotalPages();
        elementsPag = page.getSize();
        pagActual = page.getNumber()+1;
        
        int desde, hasta;
        desde = 1;
        hasta = totalPag;
        
        for(int i=0; i<hasta; i++){
            paginas.add(new ElementsPage(desde + i, pagActual == desde+i));
            
        }
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalPag() {
        return totalPag;
    }

    public void setTotalPag(int totalPag) {
        this.totalPag = totalPag;
    }

    public int getPagActual() {
        return pagActual;
    }

    public void setPagActual(int pagActual) {
        this.pagActual = pagActual;
    }

    public List<ElementsPage> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<ElementsPage> paginas) {
        this.paginas = paginas;
    }
}
