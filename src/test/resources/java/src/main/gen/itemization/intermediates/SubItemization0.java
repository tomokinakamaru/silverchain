package itemization.intermediates;

public interface SubItemization0<INNER, ITEM> {

  itemization.EmptySubItemization<itemization.SubItemization<INNER, ITEM>> begin();

  INNER end();

  itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
