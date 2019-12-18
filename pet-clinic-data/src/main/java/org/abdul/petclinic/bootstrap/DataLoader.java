package org.abdul.petclinic.bootstrap;

import org.abdul.petclinic.map.OwnerServiceMap;
import org.abdul.petclinic.map.VetServiceMap;
import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Vet;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
   private OwnerService ownerService;
   private VetService vetService;

   public DataLoader() {
      ownerService = new OwnerServiceMap();
      vetService = new VetServiceMap();
   }

   @Override
   public void run(String... args) throws Exception {
      Owner owner1 = new Owner();
      owner1.setId(1L);
      owner1.setFirstName("Abdul Katheer");
      owner1.setLastName("M");

      Owner owner2 = new Owner();
      owner2.setId(2L);
      owner2.setFirstName("Dhanalakshmi");
      owner2.setLastName("N");

      Owner owner3 = new Owner();
      owner3.setId(2L);
      owner3.setFirstName("Vinothkumar");
      owner3.setLastName("N");

      Vet vet1 = new Vet();
      vet1.setId(1L);
      vet1.setFirstName("Hari");
      vet1.setLastName("K");

      Vet vet2 = new Vet();
      vet2.setId(2L);
      vet2.setFirstName("Nirmal");
      vet2.setLastName("G");

      ownerService.save(owner1);
      ownerService.save(owner2);
      ownerService.save(owner3);

      vetService.save(vet1);
      vetService.save(vet2);
   }
}
