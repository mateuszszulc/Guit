package com.guit.client.dom;

import com.guit.client.Implementation;
import com.guit.client.dom.impl.PreImpl;
import com.guit.client.junit.Mock;
import com.guit.junit.dom.PreMock;

@Implementation(PreImpl.class)
@Mock(PreMock.class)
public interface Pre extends Element {
}